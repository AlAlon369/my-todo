
package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;

import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
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

        CrudFormFactory<Employee> formFactory = new DefaultCrudFormFactory<>(Employee.class);
        formFactory.setVisibleProperties(CrudOperation.ADD, "firstName", "lastName", "middleName", "phone");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "firstName", "lastName", "middleName", "phone");
        crud.setCrudFormFactory(formFactory);

        add(crud);
    }
}

