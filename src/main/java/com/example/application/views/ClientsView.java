package com.example.application.views;

import com.example.application.data.entity.Client;
import com.example.application.data.repository.ClientRepository;
import com.vaadin.flow.component.checkbox.Checkbox;
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
        crudFormFactory.setVisibleProperties("firstName", "lastName","company", "phone", "email", "Working");
        crudFormFactory.setFieldCreationListener("firstName", field -> ((TextField) field).setLabel("Компания"));
        crudFormFactory.setFieldCreationListener("lastName", field -> ((TextField) field).setLabel("Имя"));
        crudFormFactory.setFieldCreationListener("company", field -> ((TextField) field).setLabel("Фамилия"));
        crudFormFactory.setFieldCreationListener("phone", field -> ((TextField) field).setLabel("Телефон"));
        crudFormFactory.setFieldCreationListener("email", field -> ((TextField) field).setLabel("Электронная почта"));
        crudFormFactory.setFieldCreationListener("working", field -> ((Checkbox) field).setLabel("Работаем"));
    }

    private void setColumns(GridCrud<Client> crud) {
        Grid<Client> grid = crud.getGrid();
        grid.getColumnByKey("firstName").setHeader("Имя");
        grid.getColumnByKey("lastName").setHeader("Фамилия");
        grid.getColumnByKey("company").setHeader("Компания");
        grid.getColumnByKey("phone").setHeader("Телефон");
        grid.getColumnByKey("email").setHeader("Электронная почта");
        grid.getColumnByKey("working").setVisible(false);
        grid.getColumnByKey("id").setVisible(false);
        grid.addColumn(client -> Boolean.TRUE.equals(client.getWorking()) ? "Да" : "Нет").setHeader("Работаем");
    }
}

