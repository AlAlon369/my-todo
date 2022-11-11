package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.example.application.data.entity.Employee;
import com.example.application.data.entity.OperationAccounting;
import com.example.application.data.entity.OperationTimeSheet;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.OperationAccountingRepository;
import com.example.application.data.repository.OperationTimeSheetRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "operation_timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OperationTimeSheetView extends FormLayout implements HasUrlParameter<Integer> {

  private final transient OperationTimeSheetRepository repository;
  private final transient OperationAccountingRepository accountingRepository;
  private final GridCrud<OperationTimeSheet> crud;

  public OperationTimeSheetView(OperationTimeSheetRepository repository,
                                OperationAccountingRepository accountingRepository,
                                EmployeeRepository employeeRepository
  ) {
    this.repository = repository;
    this.accountingRepository = accountingRepository;
    crud = new GridCrud<>(OperationTimeSheet.class);
    crud.setUpdateOperation(repository::save);
    crud.setDeleteOperation(repository::delete);

    Grid<OperationTimeSheet> grid = crud.getGrid();
    grid.removeColumnByKey("id");
    grid.removeColumnByKey("operationAccounting");
    grid.removeColumnByKey("employee");
    Grid.Column<OperationTimeSheet> hours = grid.getColumnByKey("hours").setHeader("Часы");
    Grid.Column<OperationTimeSheet> employee = crud.getGrid()
      .addColumn(user -> user.getEmployee().getLastName())
      .setHeader("Сотрудник");
    grid.setColumnOrder(List.of(employee, hours));

    CrudFormFactory<OperationTimeSheet> crudFormFactory = crud.getCrudFormFactory();
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

    Button back = new Button("Назад", new Icon(VaadinIcon.ARROW_LEFT));
    back.addClickListener(e -> this.getUI().ifPresent(ui -> ui.navigate(OperationAccountingView.class)));
    VerticalLayout verticalLayout = new VerticalLayout(back, crud);
    add(verticalLayout);
  }

  @Override
  public void setParameter(BeforeEvent event, Integer id) {
    OperationAccounting operationAccounting = accountingRepository.findById(id).orElseThrow();
    crud.setFindAllOperation(() -> repository.findByOperationAccounting(operationAccounting));
    crud.setAddOperation(entity -> {
      entity.setOperationAccounting(operationAccounting);
      return repository.save(entity);
    });
    crud.refreshGrid();
  }
}
