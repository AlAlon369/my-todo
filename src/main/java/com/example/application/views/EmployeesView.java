
package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;

@Route(value = "employees", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class EmployeesView extends VerticalLayout {

    public EmployeesView(EmployeeRepository repository) {
        GridCrud<Employee> crud = new GridCrud<>(Employee.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        setColumns(crud);
        translateForms(crud);
        crud.setWidth("50%");
        setSizeFull();
        add(crud);
    }

    private void translateForms(GridCrud<Employee> crud) {
        CrudFormFactory<Employee> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("firstName", "lastName", "phone", "hired");
        crudFormFactory.setFieldCreationListener("firstName", field -> ((TextField) field).setLabel("Имя"));
        crudFormFactory.setFieldCreationListener("lastName", field -> ((TextField) field).setLabel("Фамилия"));
        crudFormFactory.setFieldCreationListener("phone", field -> ((TextField) field).setLabel("Телефон"));
        crudFormFactory.setFieldCreationListener("hired", field -> ((Checkbox) field).setLabel("Нанят"));
    }

    private void setColumns(GridCrud<Employee> crud) {
        Grid<Employee> grid = crud.getGrid();
        grid.getColumnByKey("firstName").setHeader("Имя");
        Grid.Column<Employee> employeeColumn = grid.getColumnByKey("lastName").setHeader("Фамилия");
        grid.getColumnByKey("phone").setHeader("Телефон");
        grid.getColumnByKey("hired").setVisible(false);
        grid.getColumnByKey("id").setVisible(false);
        grid.addColumn(employee -> Boolean.TRUE.equals(employee.getHired()) ? "Да" : "Нет").setHeader("Нанят");
        GridSortOrder<Employee> employeeSort = new GridSortOrder<>(employeeColumn, SortDirection.ASCENDING);
        grid.sort(List.of(employeeSort));
    }
}

