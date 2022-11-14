package com.example.application.views;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.example.application.data.entity.Operation;
import com.example.application.data.entity.OperationAccounting;
import com.example.application.data.entity.OperationTimeSheet;
import com.example.application.data.repository.OperationAccountingRepository;
import com.example.application.data.repository.OperationRepository;
import com.example.application.data.repository.RateRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "operation_accounting", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OperationAccountingView extends VerticalLayout {
  public OperationAccountingView(OperationAccountingRepository repository,
                                 OperationRepository operationRepository,
                                 RateRepository rateRepository) {
    GridCrud<OperationAccounting> crud = new GridCrud<>(OperationAccounting.class);
    DatePicker filter = createFilter(crud);
    crud.setFindAllOperation(() -> repository.findAllByDate(filter.getValue()));
    crud.setAddOperation(entity -> {
      entity.setDate(filter.getValue());
      return repository.save(entity);
    });
    crud.setUpdateOperation(repository::save);
    crud.setDeleteOperation(repository::delete);

    tuneColumns(crud);
    tuneFields(operationRepository, rateRepository, crud);

    crud.setWidth("70%");
    setSizeFull();
    add(crud);
  }

  private DatePicker createFilter(GridCrud<OperationAccounting> crud) {
    DatePicker filter = new DatePicker();
    filter.setValue(LocalDate.now());
    filter.addValueChangeListener(e -> crud.refreshGrid());
    crud.getCrudLayout().addFilterComponent(filter);
    return filter;
  }

  private void tuneColumns(GridCrud<OperationAccounting> crud) {
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
    Grid.Column<OperationAccounting> timeFact = grid.addColumn(this::getTimeSheetsSum).setHeader("Часы");
    Grid.Column<OperationAccounting> clock = grid.addComponentColumn(accounting -> {
      Button timeButton = new Button("Часы");
      timeButton.addClickListener(e ->
        this.getUI().ifPresent(ui ->
          ui.navigate(
            OperationTimeSheetView.class,
            accounting.getId()
          )));
      return timeButton;
    }).setWidth("150px").setFlexGrow(0);
    grid.setColumnOrder(List.of(operationName, plan, fact, rateMultiplyHours, rateColumn, timeFact, clock));
    GridSortOrder<OperationAccounting> operationSort = new GridSortOrder<>(operationName, SortDirection.ASCENDING);
    grid.sort(List.of(operationSort));
  }

  private void tuneFields(OperationRepository operationRepository,
                          RateRepository rateRepository,
                          GridCrud<OperationAccounting> crud
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
