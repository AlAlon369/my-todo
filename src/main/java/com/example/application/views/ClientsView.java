package com.example.application.views;

import com.example.application.data.entity.Client;
import com.example.application.data.repository.ClientRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import javax.annotation.security.RolesAllowed;

@Route(value = "clients", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class ClientsView extends FormLayout {

    public ClientsView(ClientRepository repository) {
        GridCrud<Client> crud = new GridCrud<>(Client.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        setColumns(crud);
        translateForms(crud);

        add(crud);
    }

    private void translateForms(GridCrud<Client> crud) {
        CrudFormFactory<Client> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("company");
        crudFormFactory.setFieldCreationListener("company", field -> ((TextField) field).setLabel("Компания"));
    }

    private void setColumns(GridCrud<Client> crud) {
        Grid<Client> grid = crud.getGrid();
        grid.getColumnByKey("company").setHeader("Компания");
        grid.getColumnByKey("id").setVisible(false);
    }
}

