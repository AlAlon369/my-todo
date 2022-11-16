package com.example.application.views;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.example.application.data.entity.Employee;
import com.example.application.data.entity.Operation;
import com.example.application.data.entity.OperationAccounting;
import com.example.application.data.entity.OperationTimeSheet;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.OperationAccountingRepository;
import com.example.application.data.repository.OperationRepository;
import com.example.application.data.repository.OperationTimeSheetRepository;
import com.example.application.data.repository.RateRepository;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "operation_accounting", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OperationAccountingView extends VerticalLayout {

  private final transient OperationAccountingRepository repository;
  private final transient OperationRepository operationRepository;
  private final transient RateRepository rateRepository;
  private final transient OperationTimeSheetRepository timeSheetRepository;
  private final transient EmployeeRepository employeeRepository;


  public OperationAccountingView(OperationAccountingRepository repository,
                                 OperationRepository operationRepository,
                                 RateRepository rateRepository,
                                 OperationTimeSheetRepository timeSheetRepository,
                                 EmployeeRepository employeeRepository) {
    this.repository = repository;
    this.operationRepository = operationRepository;
    this.rateRepository = rateRepository;
    this.timeSheetRepository = timeSheetRepository;
    this.employeeRepository = employeeRepository;

    GridCrud<OperationAccounting> accountingGridCrud = new GridCrud<>(OperationAccounting.class);
    DatePicker filter = createFilter(accountingGridCrud);
    tuneAccountingGridCrud(accountingGridCrud, filter);
    tuneAccountingColumns(accountingGridCrud);
    tuneAccountingFields(accountingGridCrud);

    GridCrud<OperationTimeSheet> timeSheetGridCrud = createTimeSheetGridCrud(accountingGridCrud);
    tuneTimeSheetColumns(timeSheetGridCrud);
    tuneTimeSheetFields(timeSheetGridCrud);

    accountingGridCrud.getGrid().addSelectionListener(event -> {
      timeSheetGridCrud.setVisible(!event.getAllSelectedItems().isEmpty());
      timeSheetGridCrud.refreshGrid();
    });

    HorizontalLayout horizontalLayout = new HorizontalLayout(accountingGridCrud, timeSheetGridCrud);
    horizontalLayout.setSizeFull();
    setSizeFull();

    add(horizontalLayout);
  }

  private GridCrud<OperationTimeSheet> createTimeSheetGridCrud(GridCrud<OperationAccounting> accountingGridCrud) {
    GridCrud<OperationTimeSheet> timeSheetGridCrud = new GridCrud<>(OperationTimeSheet.class);
    timeSheetGridCrud.setVisible(false);
    timeSheetGridCrud.setAddOperation(entity -> saveTimeSheet(timeSheetRepository, accountingGridCrud, entity));
    timeSheetGridCrud.setUpdateOperation(entity -> saveTimeSheet(timeSheetRepository, accountingGridCrud, entity));
    timeSheetGridCrud.setDeleteOperation(entity -> {
      Set<OperationAccounting> items = accountingGridCrud.getGrid().getSelectedItems();
      OperationAccounting operationAccounting = items.toArray(new OperationAccounting[0])[0];
      entity.setOperationAccounting(operationAccounting);
      timeSheetRepository.delete(entity);
      accountingGridCrud.refreshGrid();
      accountingGridCrud.getGrid().select(operationAccounting);
    });
    timeSheetGridCrud.setFindAllOperation(() -> {
      Set<OperationAccounting> items = accountingGridCrud.getGrid().getSelectedItems();
      if (items.isEmpty()) return Collections.emptyList();
      return timeSheetRepository.findByOperationAccounting(items.toArray(new OperationAccounting[0])[0]);
    });
    timeSheetGridCrud.setWidth("30%");
    return timeSheetGridCrud;
  }

  private OperationTimeSheet saveTimeSheet(OperationTimeSheetRepository timeSheetRepository,
                                           GridCrud<OperationAccounting> accountingGridCrud,
                                           OperationTimeSheet entity) {
    Set<OperationAccounting> items = accountingGridCrud.getGrid().getSelectedItems();
    OperationAccounting operationAccounting = items.toArray(new OperationAccounting[0])[0];
    entity.setOperationAccounting(operationAccounting);
    OperationTimeSheet save = timeSheetRepository.save(entity);
    accountingGridCrud.refreshGrid();
    accountingGridCrud.getGrid().select(operationAccounting);
    return save;
  }

  private void tuneAccountingGridCrud(GridCrud<OperationAccounting> accountingGridCrud,
                                      DatePicker filter) {
    accountingGridCrud.setFindAllOperation(() -> repository.findAllByDate(filter.getValue()));
    accountingGridCrud.setAddOperation(entity -> {
      entity.setDate(filter.getValue());
      return repository.save(entity);
    });
    accountingGridCrud.setUpdateOperation(repository::save);
    accountingGridCrud.setDeleteOperation(repository::delete);
    accountingGridCrud.setWidth("70%");
  }

  private void tuneTimeSheetFields(GridCrud<OperationTimeSheet> timeSheetGridCrud) {
    CrudFormFactory<OperationTimeSheet> crudFormFactory = timeSheetGridCrud.getCrudFormFactory();
    crudFormFactory.setFieldCreationListener("id", field -> ((TextField) field).setVisible(false));
    crudFormFactory.setFieldCreationListener("hours", field -> ((TextField) field).setLabel("Часы"));
    crudFormFactory.setFieldCreationListener("employee", field -> ((ComboBox<?>) field).setLabel("Сотрудник"));
    crudFormFactory.setFieldProvider("employee",
      new ComboBoxProvider<>(
        "Сотрудник",
        employeeRepository.findByHiredTrue(),
        new TextRenderer<>(Employee::getLastName),
        Employee::getLastName
      ));
  }

  private void tuneTimeSheetColumns(GridCrud<OperationTimeSheet> timeSheetGridCrud) {
    Grid<OperationTimeSheet> timeSheetGrid = timeSheetGridCrud.getGrid();
    timeSheetGrid.removeColumnByKey("id");
    timeSheetGrid.removeColumnByKey("operationAccounting");
    timeSheetGrid.removeColumnByKey("employee");
    Grid.Column<OperationTimeSheet> hours = timeSheetGrid.getColumnByKey("hours").setHeader("Часы");
    Grid.Column<OperationTimeSheet> employee = timeSheetGrid
      .addColumn(user -> user.getEmployee().getLastName())
      .setHeader("Сотрудник");
    timeSheetGrid.setColumnOrder(List.of(employee, hours));
  }

  private DatePicker createFilter(GridCrud<OperationAccounting> crud) {
    DatePicker filter = new DatePicker();
    filter.setValue(LocalDate.now());
    filter.addValueChangeListener(e -> crud.refreshGrid());
    crud.getCrudLayout().addFilterComponent(filter);
    return filter;
  }

  private void tuneAccountingColumns(GridCrud<OperationAccounting> crud) {
    Grid<OperationAccounting> grid = crud.getGrid();
    grid.removeColumnByKey("id");
    grid.removeColumnByKey("timeSheets");
    grid.removeColumnByKey("operation");
    grid.removeColumnByKey("rate");
    grid.removeColumnByKey("date");
    Grid.Column<OperationAccounting> fact = grid.getColumnByKey("fact").setHeader("Факт");
    Grid.Column<OperationAccounting> plan = grid.getColumnByKey("plan").setHeader("План");
    Grid.Column<OperationAccounting> operationName = grid
      .addColumn(accounting -> accounting.getOperation().getTitle())
      .setHeader("Операция")
      .setAutoWidth(true);
    Grid.Column<OperationAccounting> rateColumn = grid
      .addColumn(accounting -> accounting.getRate() != null ? accounting.getRate().getAmount() : 0)
      .setHeader("Норма");
    Grid.Column<OperationAccounting> rateMultiplyHours = grid
      .addColumn(accounting -> accounting.getRate() != null ? accounting.getRate().getAmount() * getTimeSheetsSum(accounting) : 0)
      .setHeader("Норма*Часы")
      .setAutoWidth(true);
    Grid.Column<OperationAccounting> timeFact = grid.addColumn(this::getTimeSheetsSum).setHeader("Часы").setKey("timeFact");
    grid.setColumnOrder(List.of(operationName, plan, fact, rateMultiplyHours, rateColumn, timeFact));
    GridSortOrder<OperationAccounting> operationSort = new GridSortOrder<>(operationName, SortDirection.ASCENDING);
    grid.sort(List.of(operationSort));
  }

  private void tuneAccountingFields(GridCrud<OperationAccounting> crud
  ) {
    CrudFormFactory<OperationAccounting> crudFormFactory = crud.getCrudFormFactory();
    crudFormFactory.setUseBeanValidation(true);
    crudFormFactory.setFieldCreationListener("plan", field -> ((TextField) field).setLabel("План"));
    crudFormFactory.setFieldCreationListener("fact", field -> ((TextField) field).setLabel("Факт"));
    crudFormFactory.setFieldCreationListener("rate", field -> ((ComboBox<?>) field).setLabel("Норма"));
    crudFormFactory.setFieldCreationListener("operation", field -> ((ComboBox<?>) field).setLabel("Операция"));
    crudFormFactory.setVisibleProperties("operation", "plan", "fact", "rate");
    crudFormFactory.setFieldProvider("operation",
      new ComboBoxProvider<>(
        "Операция",
        operationRepository.findAllByOrderByTitleAsc(),
        new TextRenderer<>(Operation::getTitle),
        Operation::getTitle
      ));
    crudFormFactory.setFieldProvider("rate",
      new ComboBoxProvider<>(
        "Норма",
        rateRepository.findAllByOrderByOperationAscAmountAsc(),
        new TextRenderer<>(rate -> rate.getOperation().getTitle() + ": " + rate.getAmount()),
        rate -> rate.getOperation().getTitle() + ": " + rate.getAmount()
      ));
  }

  private double getTimeSheetsSum(OperationAccounting accounting) {
    return accounting.getTimeSheets() != null
      ? accounting.getTimeSheets().stream()
      .mapToDouble(OperationTimeSheet::getHours)
      .sum()
      : 0;
  }
}
