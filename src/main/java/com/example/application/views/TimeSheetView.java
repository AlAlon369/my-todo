package com.example.application.views;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.example.application.data.entity.Product;
import com.example.application.data.entity.TimeSheet;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.ProductRepository;
import com.example.application.data.repository.TimeSheetRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {

    public TimeSheetView(TimeSheetRepository repository,
                         ProductRepository productRepository,
                         EmployeeRepository employeeRepository
    ) {
        GridCrud<TimeSheet> gridCrud = new GridCrud<>(TimeSheet.class);
        gridCrud.setFindAllOperation(repository::findAll);
        gridCrud.setAddOperation(repository::save);
        gridCrud.setUpdateOperation(repository::save);
        gridCrud.setDeleteOperation(repository::delete);

        Grid<TimeSheet> grid = gridCrud.getGrid();
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("employee");
        grid.removeColumnByKey("product");
        Grid.Column<TimeSheet> hours = grid.getColumnByKey("hours").setHeader("Часы");
        Grid.Column<TimeSheet> date = grid.getColumnByKey("date").setHeader("Дата");
        Grid.Column<TimeSheet> product = grid.addColumn(user -> user.getProduct().getTitle()).setHeader("Продукт");
        Grid.Column<TimeSheet> employee = grid
          .addColumn(user -> user.getEmployee().getLastName() + " " + user.getEmployee().getFirstName())
          .setHeader("Сотрудник");
        grid.setColumnOrder(List.of(employee, date, hours, product));

        CrudFormFactory<TimeSheet> crudFormFactory = gridCrud.getCrudFormFactory();
        crudFormFactory.setFieldCreationListener("date",
          field -> {
            DatePicker datePicker = (DatePicker) field;
            datePicker.setLabel("Дата");
            datePicker.setValue(LocalDate.now());
          });
        crudFormFactory.setFieldCreationListener("employee", field -> ((ComboBox<?>) field).setLabel("Сотрудник"));
        crudFormFactory.setFieldCreationListener("hours", field -> ((TextField) field).setLabel("Часы"));
        crudFormFactory.setFieldCreationListener("product", field -> ((ComboBox<?>) field).setLabel("Продукт"));
        crudFormFactory.setVisibleProperties("employee", "date", "hours", "product");
        crudFormFactory.setFieldProvider("product",
          new ComboBoxProvider<>(
            "Продукт",
            productRepository.findAll(),
            new TextRenderer<>(Product::getTitle),
            Product::getTitle
          ));
        crudFormFactory.setFieldProvider("employee",
          new ComboBoxProvider<>(
            "Сотрудник",
            employeeRepository.findAll(),
            new TextRenderer<>(item -> item.getLastName() + " " + item.getFirstName()),
            item -> item.getLastName() + " " + item.getFirstName()
          ));

        add(gridCrud);
    }
}
