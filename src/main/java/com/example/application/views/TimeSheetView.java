package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.application.controller.TimeSheetController;
import com.example.application.data.TimeSheetDto;
import com.example.application.data.entity.TimeSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {
    public TimeSheetView(TimeSheetController controller) {
        GridCrud<TimeSheetDto> crud = new GridCrud<>(TimeSheetDto.class);
        crud.setFindAllOperation(controller::findAll);
        crud.setAddOperation(controller::save);

        Grid<TimeSheetDto> grid = crud.getGrid();
        grid.removeColumnByKey("employee");
        grid.removeColumnByKey("timeSheets");
        grid.addColumn(dto -> dto.getEmployee().getFirstName() + " " + dto.getEmployee().getLastName())
          .setHeader("Фамилия, имя")
          .setAutoWidth(true);

        for (int i = 0; i < 7; i++) {
            int finalI = i;
            grid.addColumn(dto -> {
                  List<TimeSheet> timeSheets = dto.getTimeSheets();
                  if (finalI < timeSheets.size()) {
                      return timeSheets.get(finalI).getHours();
                  }
                  return null;
              })
              .setHeader(25 + i + ".10")
              .setAutoWidth(true);
        }

        add(crud);
    }
}
