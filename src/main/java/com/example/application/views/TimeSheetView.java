package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.application.data.entity.TimeSheet;
import com.example.application.data.repository.TimeSheetRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {

    public TimeSheetView(TimeSheetRepository repository) {
        GridCrud<TimeSheet> gridCrud = new GridCrud<>(TimeSheet.class);
        gridCrud.setFindAllOperation(repository::findAll);
        gridCrud.setAddOperation(repository::save);
        gridCrud.setUpdateOperation(repository::save);
        gridCrud.setDeleteOperation(repository::delete);

        Grid<TimeSheet> grid = gridCrud.getGrid();

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("employee");
        grid.removeColumnByKey("product");
        Grid.Column<TimeSheet> hours = grid.getColumnByKey("hours");
        Grid.Column<TimeSheet> date = grid.getColumnByKey("date");
        Grid.Column<TimeSheet> product = grid.addColumn(user -> user.getProduct().getTitle()).setHeader("Продукт");
        Grid.Column<TimeSheet> employee = grid.addColumn(
        user -> user.getEmployee().getLastName() + " " + user.getEmployee().getFirstName()).setHeader(
        "Сотрудник");

        grid.setColumnOrder(List.of(employee, date, hours, product));

        add(gridCrud);
    }
}
