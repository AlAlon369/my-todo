package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "add-employees", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class CreateEmployeeView extends VerticalLayout {
  private final transient EmployeeRepository repository;
  private final transient Employee employee = new Employee();

  TextField firstName = new TextField("имя");
  TextField lastName = new TextField("фамилия");
  TextField middleName = new TextField("отчество");
  TextField phone = new TextField("телефон");
  Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);
  Button save = new Button("Сохранить");

  public CreateEmployeeView(EmployeeRepository employeeRepository) {
    binder.bindInstanceFields(this);
    binder.setBean(employee);
    this.repository = employeeRepository;
    addClassName("contact-form");
    add(
      firstName,
      lastName,
      middleName,
      phone,
      createButtonLayout()
    );
  }

  private Component createButtonLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addClickShortcut(Key.ENTER);
    save.addClickListener(event -> {
      repository.save(employee);
      Notification notification = Notification.show("Сотрудник добавлен!");
      notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    });
    return new HorizontalLayout(save);
  }
}
