
package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "employees", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class EmployeesView extends FormLayout {



    public EmployeesView(EmployeeRepository employeeRepository) {
        Grid<Employee> grid = new Grid<>(Employee.class, false);
        grid.addColumn(Employee::getFirstName).setHeader("First name");
        grid.addColumn(Employee::getLastName).setHeader("Last name");
        grid.addColumn(Employee::getMiddleName).setHeader("Middle name");
        grid.addColumn(Employee::getPhone).setHeader("Phone");

        List<Employee> all = employeeRepository.findAll();
        grid.setItems(all);

        Button button = new Button("Добавить сотрудника");
        button.addClickListener(e ->
                button.getUI().ifPresent(ui ->
                        ui.navigate("add-employees"))
        );
        HorizontalLayout horizontalLayout = new HorizontalLayout(button);
        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, grid);
        add(verticalLayout);
    }
}

