
package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "employees", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class EmployeesView extends FormLayout {

    public EmployeesView(EmployeeRepository repository) {

        GridCrud<Employee> crud = new GridCrud<>(Employee.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        translateColumns(crud);
        translateForms(crud);

        add(crud);
    }

    private  void translateForms(GridCrud<Employee> crud) {
        CrudFormFactory<Employee> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("firstName", "lastName", "middleName", "phone");
        crudFormFactory.setFieldCreationListener("firstName", field -> ((TextField) field).setLabel("Имя"));
        crudFormFactory.setFieldCreationListener("lastName", field -> ((TextField) field).setLabel("Фамилия"));
    }

    private void translateColumns(GridCrud<Employee> crud) {
        crud.getGrid().getColumnByKey("firstName").setHeader("Имя");
        crud.getGrid().getColumnByKey("lastName").setHeader("Фамилия");
    }
}

