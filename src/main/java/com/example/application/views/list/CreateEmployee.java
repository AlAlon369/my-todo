package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.List;

//@Route(value = "add-employee")
@Route(value = "")
@RolesAllowed("USER")
public class CreateEmployee extends FormLayout {
    TextField firstName = new TextField("имя");
    TextField lastName = new TextField("фамилия");
    TextField middleName = new TextField("отчество");
    TextField phoneNumber = new TextField("телефон");




    Button save = new Button("Сохранить");

    public CreateEmployee(List<Contact> contacts) {
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
