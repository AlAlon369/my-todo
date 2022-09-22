
package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
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

        setColumns(crud);
        translateForms(crud);

        add(crud);
    }

    private void translateForms(GridCrud<Employee> crud) {
        CrudFormFactory<Employee> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("firstName", "lastName", "middleName", "phone", "hired");
        crudFormFactory.setFieldCreationListener("firstName", field -> ((TextField) field).setLabel("Имя"));
        crudFormFactory.setFieldCreationListener("lastName", field -> ((TextField) field).setLabel("Фамилия"));
        crudFormFactory.setFieldCreationListener("middleName", field -> ((TextField) field).setLabel("Отчество"));
        crudFormFactory.setFieldCreationListener("phone", field -> ((TextField) field).setLabel("Телефон"));
        crudFormFactory.setFieldCreationListener("hired", field -> ((Checkbox) field).setLabel("Нанят"));
    }

    private void setColumns(GridCrud<Employee> crud) {
        Grid<Employee> grid = crud.getGrid();
        grid.getColumnByKey("firstName").setHeader("Имя");
        grid.getColumnByKey("lastName").setHeader("Фамилия");
        grid.getColumnByKey("middleName").setHeader("Отчество");
        grid.getColumnByKey("phone").setHeader("Телефон");
        grid.getColumnByKey("hired").setVisible(false);
        grid.getColumnByKey("id").setVisible(false);
        grid.addColumn(employee -> employee.isHired() ? "Да" : "Нет").setHeader("Нанят");
    }
}

