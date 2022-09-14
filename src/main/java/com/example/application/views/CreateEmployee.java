package com.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "add-employees")
@RolesAllowed("USER")
public class CreateEmployee extends VerticalLayout {
    TextField firstName = new TextField("имя");
    TextField lastName = new TextField("фамилия");
    TextField middleName = new TextField("отчество");
    TextField phoneNumber = new TextField("телефон");


    Button save = new Button("Сохранить");

    public CreateEmployee() {
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
