package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import com.example.application.data.entity.Operation;
import com.example.application.data.repository.OperationRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;

@Route(value = "operation", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OperationView extends VerticalLayout {
    public OperationView(OperationRepository repository) {
        GridCrud<Operation> crud = new GridCrud<>(Operation.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Operation> grid = crud.getGrid();
        Grid.Column<Operation> operationColumn = grid.getColumnByKey("title").setHeader("Операция");
        grid.removeColumnByKey("id");
        GridSortOrder<Operation> operationSort = new GridSortOrder<>(operationColumn, SortDirection.ASCENDING);
        grid.sort(List.of(operationSort));

        CrudFormFactory<Operation> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("title");
        crudFormFactory.setFieldCreationListener("title", field -> ((TextField) field).setLabel("Операция"));
        crud.setWidth("50%");
        setSizeFull();
        add(crud);
    }
}
