package com.example.application.views;

import com.example.application.data.entity.GeneralTimeSheet;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.GeneralTimeSheetRepository;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.List;

@Route(value = "general-time-sheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class GeneralTimeSheetView extends FormLayout {

    public GeneralTimeSheetView(GeneralTimeSheetRepository repository, EmployeeRepository employeeRepository) {
        GridCrud<GeneralTimeSheet> gridCrud = new GridCrud<>(GeneralTimeSheet.class);
        gridCrud.setFindAllOperation(repository::findAll);
        gridCrud.setAddOperation(repository::save);
        gridCrud.setUpdateOperation(repository::save);
        gridCrud.setDeleteOperation(repository::delete);

        Grid<GeneralTimeSheet> grid = gridCrud.getGrid();
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("employee");

        Grid.Column<GeneralTimeSheet> hours = grid.getColumnByKey("hours").setHeader("Часы");
        Grid.Column<GeneralTimeSheet> date = grid.getColumnByKey("date").setHeader("Дата");
        Grid.Column<GeneralTimeSheet> employee = grid
                .addColumn(user -> user.getEmployee().getLastName() + " " + user.getEmployee().getFirstName())
                .setHeader("Сотрудник");
        grid.setColumnOrder(List.of(employee, date, hours));

        CrudFormFactory<GeneralTimeSheet> crudFormFactory = gridCrud.getCrudFormFactory();
        crudFormFactory.setFieldCreationListener("date",
                field -> {
                    DatePicker datePicker = (DatePicker) field;
                    datePicker.setLabel("Дата");
                    datePicker.setValue(LocalDate.now());
                });

        crudFormFactory.setFieldCreationListener("employee", field -> ((ComboBox<?>) field).setLabel("Сотрудник"));
        crudFormFactory.setFieldCreationListener("hours", field -> ((TextField) field).setLabel("Часы"));
        crudFormFactory.setVisibleProperties("employee", "date", "hours");
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
