
package com.example.application.views.list;

import java.util.List;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "add-employee")
@RolesAllowed("USER")
public class Employees extends FormLayout {
    TextField firstName = new TextField("имя");
    TextField lastName = new TextField("фамилия");
    TextField middleName = new TextField("отчество");
    TextField phoneNumber = new TextField("телефон");


    Button save = new Button("Сохранить");


    public Employees(List<Company> companies, List<Status> statuses) {
        addClassName("contact-form");



        add(
                firstName,
                lastName,
                middleName,
                phoneNumber,
                createButtonLayout()

        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        return new HorizontalLayout(save);

    }
}

