package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.example.application.data.entity.Operation;
import com.example.application.data.entity.Rate;
import com.example.application.data.repository.OperationRepository;
import com.example.application.data.repository.RateRepository;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "rate", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class RateView extends FormLayout {
  public RateView(RateRepository repository, OperationRepository operationRepository) {
    GridCrud<Rate> crud = new GridCrud<>(Rate.class);
    crud.setFindAllOperation(repository::findAll);
    crud.setAddOperation(repository::save);
    crud.setUpdateOperation(repository::save);
    crud.setDeleteOperation(repository::delete);

    Grid<Rate> grid = crud.getGrid();
    grid.removeColumnByKey("id");
    grid.removeColumnByKey("operation");
    Grid.Column<Rate> rate = grid.getColumnByKey("amount").setHeader("Норматив");
    Grid.Column<Rate> operation = grid.addColumn(r -> r.getOperation().getTitle()).setHeader("Операция");
    grid.setColumnOrder(List.of(operation, rate));

    CrudFormFactory<Rate> crudFormFactory = crud.getCrudFormFactory();
    crudFormFactory.setFieldCreationListener("id", field -> ((TextField) field).setVisible(false));
    crudFormFactory.setFieldCreationListener("amount", field -> ((TextField) field).setLabel("Норматив"));
    crudFormFactory.setFieldCreationListener("operation", field -> ((ComboBox<?>) field).setLabel("Операция"));
    crudFormFactory.setFieldProvider("operation",
      new ComboBoxProvider<>("Операция",
        operationRepository.findAll(),
        new TextRenderer<>(Operation::getTitle),
        Operation::getTitle)
    );

    add(crud);
  }
}
