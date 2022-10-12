package com.example.application.views;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.BASELINE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Product;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;

import com.example.application.controller.TimeSheetController;
import com.example.application.data.TimeSheetDto;
import com.example.application.data.entity.Employee;
import com.example.application.data.entity.TimeSheet;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {
    private final transient TimeSheetController controller;
    private final GridCrud<TimeSheetDto> gridCrud = new GridCrud<>(TimeSheetDto.class, new VerticalCrudLayout());
    private final ComboBox<Product> productComboBox = new ComboBox<>("Продукты");

    public TimeSheetView(TimeSheetController controller) {        // инжектировал контроллер в конструктор таймшитвью
        this.controller = controller;
        tuneProductComboBox();
        tuneTimeSheetGrid();
        HorizontalLayout timesheetSaveLayout = createTimesheetSaveLayout();
        VerticalLayout mainLayout = new VerticalLayout(productComboBox, timesheetSaveLayout, gridCrud);
        add(mainLayout);
    }

    private HorizontalLayout createTimesheetSaveLayout() {
        ComboBox<Employee> employeeComboBox = createEmployeeComboBox();
        DatePicker datePicker = new DatePicker("Дата выхода");
        TextField hours = new TextField("Часы");
        Button button = createSaveButton(employeeComboBox, datePicker, hours);
        HorizontalLayout timesheetSaveLayout = new HorizontalLayout(employeeComboBox, datePicker, hours, button);
        timesheetSaveLayout.setAlignItems(BASELINE);
        return timesheetSaveLayout;
    }

    private Button createSaveButton(ComboBox<Employee> employeeComboBox,
                                    DatePicker datePicker,
                                    TextField textField) {
        Button button = new Button("Сохранить");
        button.addClickListener(clickEvent -> {
            Employee employee = employeeComboBox.getValue();
            String value = textField.getValue();
            LocalDate date = datePicker.getValue();
            TimeSheet timeSheet = new TimeSheet();
            timeSheet.setEmployee(employee);
            timeSheet.setHours(Integer.valueOf(value));
            timeSheet.setDate(date);
            timeSheet.setProduct(productComboBox.getValue());
            controller.save(timeSheet);
            gridCrud.refreshGrid();
        });
        return button;
    }

    private void tuneProductComboBox() {
        productComboBox.getStyle().set("--vaadin-combo-box-overlay-width", "350px");
        ComboBox.ItemFilter<Product> filter = (product, filterString) ->
                (product.getTitle().toLowerCase()).contains(filterString.toLowerCase());
        List<Product> productList = controller.findAllProducts();
        productComboBox.setItemLabelGenerator(Product::getTitle);
        productComboBox.setItems(filter, productList);
        productComboBox.addValueChangeListener(event -> gridCrud.refreshGrid());
    }

    private ComboBox<Employee> createEmployeeComboBox() {
        ComboBox<Employee> comboBox = new ComboBox<>("Сотрудник");
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "350px");
        ComboBox.ItemFilter<Employee> filter = (person, filterString) ->
                (person.getLastName() + " " + person.getFirstName()).toLowerCase().contains(filterString.toLowerCase());
        List<Employee> employees = controller.findAllHiredEmployees();
        comboBox.setItems(filter, employees);
        comboBox.setItemLabelGenerator(person -> person.getLastName() + " " + person.getFirstName());
        return comboBox;
    }

    private void tuneTimeSheetGrid() {
        setButtonsInvisible();
        gridCrud.setFindAllOperation(() -> controller.findAll(productComboBox.getValue()));
        setColumns();
    }

    private void setButtonsInvisible() {
        gridCrud.setAddOperationVisible(false);
        gridCrud.setDeleteOperationVisible(false);
        gridCrud.setUpdateOperationVisible(false);
        gridCrud.setFindAllOperationVisible(false);
    }

    private void setColumns() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM");
        Grid<TimeSheetDto> grid = gridCrud.getGrid();
        grid.removeAllColumns();
        grid.addColumn(sheet -> sheet.getEmployee().getLastName() + " " + sheet.getEmployee().getFirstName())
                .setAutoWidth(true).setHeader("Сотрудник");
        grid.addColumn(sheet -> sheet.getTimeSheetDay1() == null ? 0 : sheet.getTimeSheetDay1().getHours())
                .setHeader(today.minusDays(4).format(dateFormatter)).setAutoWidth(true);
        grid.addColumn(sheet -> sheet.getTimeSheetDay2() == null ? 0 : sheet.getTimeSheetDay2().getHours())
                .setHeader(today.minusDays(3).format(dateFormatter)).setAutoWidth(true);
        grid.addColumn(sheet -> sheet.getTimeSheetDay3() == null ? 0 : sheet.getTimeSheetDay3().getHours())
                .setHeader(today.minusDays(2).format(dateFormatter)).setAutoWidth(true);
        grid.addColumn(sheet -> sheet.getTimeSheetDay4() == null ? 0 : sheet.getTimeSheetDay4().getHours())
                .setHeader(today.minusDays(1).format(dateFormatter)).setAutoWidth(true);
        grid.addColumn(sheet -> sheet.getTimeSheetDay5() == null ? 0 : sheet.getTimeSheetDay5().getHours())
                .setHeader(today.minusDays(0).format(dateFormatter)).setAutoWidth(true);
    }
}
