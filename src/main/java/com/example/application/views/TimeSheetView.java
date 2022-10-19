package com.example.application.views;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import com.example.application.data.entity.Operation;
import com.example.application.data.repository.OperationRepository;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import com.example.application.data.entity.TimeSheet;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.TimeSheetRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {

    public TimeSheetView(TimeSheetRepository repository,
                         OperationRepository operationRepository,
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
        grid.removeColumnByKey("operation");
        Grid.Column<TimeSheet> hours = grid.getColumnByKey("hours").setHeader("Часы");
        Grid.Column<TimeSheet> date = grid.getColumnByKey("date").setHeader("Дата");
        Grid.Column<TimeSheet> operation = grid.addColumn(user -> user.getOperation().getTitle()).setHeader("Операция");
        Grid.Column<TimeSheet> employee = grid
          .addColumn(user -> user.getEmployee().getLastName() + " " + user.getEmployee().getFirstName())
          .setHeader("Сотрудник");
        grid.setColumnOrder(List.of(employee, date, hours, operation));

        CrudFormFactory<TimeSheet> crudFormFactory = gridCrud.getCrudFormFactory();
        crudFormFactory.setFieldCreationListener("date",
          field -> {
            DatePicker datePicker = (DatePicker) field;
            datePicker.setLabel("Дата");
            datePicker.setValue(LocalDate.now());
          });
        crudFormFactory.setFieldCreationListener("employee", field -> ((ComboBox<?>) field).setLabel("Сотрудник"));
        crudFormFactory.setFieldCreationListener("hours", field -> ((TextField) field).setLabel("Часы"));
        crudFormFactory.setFieldCreationListener("operation", field -> ((ComboBox<?>) field).setLabel("Операция"));
        crudFormFactory.setVisibleProperties("employee", "date", "hours", "operation");
        crudFormFactory.setFieldProvider("operation",
          new ComboBoxProvider<>(
            "Операция",
            operationRepository.findAll(),
            new TextRenderer<>(Operation::getTitle),
            Operation::getTitle
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
