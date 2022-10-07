package com.example.application.views;

import com.example.application.controller.TimeSheetController;
import com.example.application.data.TimeSheetDto;
import com.example.application.data.entity.Employee;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

import javax.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {
  public TimeSheetView(TimeSheetController controller) {
    ComboBox<Employee> employeeComboBox = createEmployeeComboBox(controller);
    GridCrud<TimeSheetDto> crud = createTimeSheetGrid(controller);
    VerticalLayout layout = new VerticalLayout(employeeComboBox, crud);
    add(layout);
  }

  private ComboBox<Employee> createEmployeeComboBox(TimeSheetController controller) {
    ComboBox<Employee> comboBox = new ComboBox<>("Сотрудник");
    comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "350px");
    ComboBox.ItemFilter<Employee> filter = (person, filterString) ->
      (person.getLastName() + " " + person.getFirstName()).toLowerCase().contains(filterString.toLowerCase());
    List<Employee> employees = controller.findAll()
      .stream()
      .map(TimeSheetDto::getEmployee)
      .collect(Collectors.toList());
    comboBox.setItems(filter, employees);
    comboBox.setItemLabelGenerator(person -> person.getLastName() + " " + person.getFirstName());
    return comboBox;
  }

  private GridCrud<TimeSheetDto> createTimeSheetGrid(TimeSheetController controller) {
    GridCrud<TimeSheetDto> crud = new GridCrud<>(TimeSheetDto.class, new VerticalCrudLayout());
    setButtonsInvisible(crud);
    crud.setFindAllOperation(controller::findAll);
    setColumns(crud);
    return crud;
  }

  private void setButtonsInvisible(GridCrud<TimeSheetDto> crud) {
    crud.setAddOperationVisible(false);
    crud.setDeleteOperationVisible(false);
    crud.setUpdateOperationVisible(false);
    crud.setFindAllOperationVisible(false);
  }

  private void setColumns(GridCrud<TimeSheetDto> crud) {
    LocalDate today = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM");
    Grid<TimeSheetDto> grid = crud.getGrid();
    grid.removeAllColumns();
    grid.addColumn(sheet -> sheet.getEmployee().getLastName() + " " + sheet.getEmployee().getFirstName()).setHeader("Сотрудник");
    grid.addColumn(sheet -> sheet.getTimeSheetDay1() == null ? 0 : sheet.getTimeSheetDay1().getHours())
      .setHeader(today.minusDays(4).format(dateFormatter));
    grid.addColumn(sheet -> sheet.getTimeSheetDay2() == null ? 0 : sheet.getTimeSheetDay2().getHours())
      .setHeader(today.minusDays(3).format(dateFormatter));
    grid.addColumn(sheet -> sheet.getTimeSheetDay3() == null ? 0 : sheet.getTimeSheetDay3().getHours())
      .setHeader(today.minusDays(2).format(dateFormatter));
    grid.addColumn(sheet -> sheet.getTimeSheetDay4() == null ? 0 : sheet.getTimeSheetDay4().getHours())
      .setHeader(today.minusDays(1).format(dateFormatter));
    grid.addColumn(sheet -> sheet.getTimeSheetDay5() == null ? 0 : sheet.getTimeSheetDay5().getHours())
      .setHeader(today.minusDays(0).format(dateFormatter));
  }
}
