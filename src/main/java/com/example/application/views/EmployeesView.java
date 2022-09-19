
package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "employees", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class EmployeesView extends FormLayout {

    public EmployeesView(EmployeeRepository employeeRepository) {
        Grid<Employee> grid = new Grid<>(Employee.class, false);
        Editor<Employee> editor = grid.getEditor();
        grid.setAllRowsVisible(true);
        Grid.Column<Employee> firstName = grid.addColumn(Employee::getFirstName).setHeader("First name");
        Grid.Column<Employee> lastName = grid.addColumn(Employee::getLastName).setHeader("Last name");
        Grid.Column<Employee> middleName = grid.addColumn(Employee::getMiddleName).setHeader("Middle name");
        Grid.Column<Employee> phone = grid.addColumn(Employee::getPhone).setHeader("Phone");
        Grid.Column<Employee> editColumn = grid.addComponentColumn(person -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(person);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        Binder<Employee> binder = new Binder<>(Employee.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField firstNameField = new TextField();
        firstNameField.setWidthFull();
        binder.forField(firstNameField)
                .asRequired("First name must not be empty")
                .bind(Employee::getFirstName, Employee::setFirstName);
        firstName.setEditorComponent(firstNameField);

        TextField lastNameField = new TextField();
        lastNameField.setWidthFull();
        binder.forField(lastNameField).asRequired("Last name must not be empty")
                .bind(Employee::getLastName, Employee::setLastName);
        lastName.setEditorComponent(lastNameField);

        TextField middleNameField = new TextField();
        middleNameField.setWidthFull();
        binder.forField(middleNameField).asRequired("Middle name must not be empty")
                .bind(Employee::getLastName, Employee::setMiddleName);
        middleName.setEditorComponent(middleName);

        TextField phoneField = new TextField();
        phoneField.setWidthFull();
        binder.forField(phoneField).asRequired("Phone field must not be empty")
                .bind(Employee::getPhone, Employee::setPhone);
        phone.setEditorComponent(phone);

        Button saveButton = new Button("Сохранить", e -> editor.save());
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);



        List<Employee> all = employeeRepository.findAll();
        grid.setItems(all);
        Button button = new Button("Добавить сотрудника");
        button.addClickListener(e ->
                button.getUI().ifPresent(ui ->
                        ui.navigate("add-employees"))
        );
        VerticalLayout verticalLayout = new VerticalLayout(button, grid);
        add(verticalLayout);
    }
}

