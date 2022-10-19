package com.example.application.views;

import com.example.application.data.entity.Cost;
import com.example.application.data.repository.CostRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import javax.annotation.security.RolesAllowed;

@Route(value = "cost", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class CostView extends FormLayout {
    public CostView(CostRepository repository) {
        GridCrud<Cost> crud = new GridCrud<>(Cost.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Cost> grid = crud.getGrid();
        grid.getColumnByKey("name").setHeader("Тип");
        grid.removeColumnByKey("id");

        CrudFormFactory<Cost> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("name");
        crudFormFactory.setFieldCreationListener("name", field -> ((TextField) field).setLabel("Тип"));

        add(crud);
    }
}

