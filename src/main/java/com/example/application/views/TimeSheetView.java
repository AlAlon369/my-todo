package com.example.application.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

import com.example.application.controller.TimeSheetController;
import com.example.application.data.TimeSheetDto  ;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {
  public TimeSheetView(TimeSheetController controller) {
    GridCrud<TimeSheetDto> crud = new GridCrud<>(TimeSheetDto.class, new VerticalCrudLayout());
    crud.setFindAllOperation(controller::findAll);
    crud.setAddOperation(controller::save);
    crud.setUpdateOperation(controller::save);

    translateForms(crud);
    setColumns(crud);

    add(crud);
  }

  private void translateForms(GridCrud<TimeSheetDto> crud) {
    CrudFormFactory<TimeSheetDto> crudFormFactory = crud.getCrudFormFactory();
    crudFormFactory.setVisibleProperties(
      "hoursDay1",
      "hoursDay2",
      "hoursDay3",
      "hoursDay4",
      "hoursDay5"
    );

    LocalDate today = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM");
    crudFormFactory.setFieldCreationListener("hoursDay1",
      field -> ((TextField) field).setLabel(today.minusDays(4).format(dateFormatter)));
    crudFormFactory.setFieldCreationListener("hoursDay2",
      field -> ((TextField) field).setLabel(today.minusDays(3).format(dateFormatter)));
    crudFormFactory.setFieldCreationListener("hoursDay3",
      field -> ((TextField) field).setLabel(today.minusDays(2).format(dateFormatter)));
    crudFormFactory.setFieldCreationListener("hoursDay4",
      field -> ((TextField) field).setLabel(today.minusDays(1).format(dateFormatter)));
    crudFormFactory.setFieldCreationListener("hoursDay5", field -> ((TextField) field).setLabel(today.format(dateFormatter)));
  }

  private void setColumns(GridCrud<TimeSheetDto> crud) {
    LocalDate today = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM");
    Grid<TimeSheetDto> grid = crud.getGrid();
    grid.getColumnByKey("fio").setHeader("Сотрудник").setAutoWidth(true);
    grid.getColumnByKey("hoursDay1").setHeader(today.minusDays(4).format(dateFormatter));
    grid.getColumnByKey("hoursDay2").setHeader(today.minusDays(3).format(dateFormatter));
    grid.getColumnByKey("hoursDay3").setHeader(today.minusDays(2).format(dateFormatter));
    grid.getColumnByKey("hoursDay4").setHeader(today.minusDays(1).format(dateFormatter));
    grid.getColumnByKey("hoursDay5").setHeader(today.format(dateFormatter));
  }
}
