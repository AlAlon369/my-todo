package com.example.application.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

import com.example.application.controller.TimeSheetController;
import com.example.application.data.TimeSheetDto;
import com.example.application.data.entity.TimeSheet;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {
  public TimeSheetView(TimeSheetController controller) {
    GridCrud<TimeSheetDto> crud = new GridCrud<>(TimeSheetDto.class, new VerticalCrudLayout());
    crud.setFindAllOperation(controller::findAll);
    crud.setUpdateOperation(controller::save);

    translateForms(crud);
    setColumns(crud);

    add(crud);
  }

  private void translateForms(GridCrud<TimeSheetDto> crud) {
    CrudFormFactory<TimeSheetDto> crudFormFactory = crud.getCrudFormFactory();
    crudFormFactory.setVisibleProperties("timeSheetDay10");
    crudFormFactory.setFieldProvider("timeSheetDay10", user -> {
      TextField textField = new TextField();
      textField.setLabel("День 1");
      return textField;
    });

    crudFormFactory.setConverter("timeSheetDay10", new Converter<Integer, TimeSheet>() {
      @Override
      public Result<TimeSheet> convertToModel(Integer value, ValueContext valueContext) {
        HasValue<?, ?> hasValue = valueContext.getHasValue().get();
        Object value1 = hasValue.getValue();
        return Result.ok((TimeSheet) value1); // error handling omitted
      }

      @Override
      public Integer convertToPresentation(TimeSheet value, ValueContext valueContext) {
        if (value == null) {
          return 0;
        }
        return value.getHours();
      }
    });
  }

  private void setColumns(GridCrud<TimeSheetDto> crud) {
    LocalDate today = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM");
    Grid<TimeSheetDto> grid = crud.getGrid();
    grid.removeAllColumns();
    grid.addColumn(sheet -> sheet.getEmployee().getLastName()).setHeader("Сотрудник");
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
