package com.example.application.views;

import com.example.application.data.entity.Technology;
import com.example.application.data.repository.TechnologyRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import javax.annotation.security.RolesAllowed;

@Route(value = "technology", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TechnologyView extends FormLayout {
    public TechnologyView(TechnologyRepository repository) {
        GridCrud<Technology> crud = new GridCrud<>(Technology.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Technology> grid = crud.getGrid();
        grid.getColumnByKey("title").setHeader("Название");
        grid.removeColumnByKey("id");

        CrudFormFactory<Technology> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("title");
        crudFormFactory.setFieldCreationListener("title", field -> ((TextField) field).setLabel("Название"));

        add(crud);
    }
}
