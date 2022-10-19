package com.example.application.views;

import java.time.LocalDate;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.example.application.data.entity.Cost;
import com.example.application.data.entity.CostAccounting;
import com.example.application.data.repository.CostAccountingRepository;
import com.example.application.data.repository.CostRepository;
import com.example.application.data.repository.OutputRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "cost_accounting", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class CostAccountingView extends FormLayout {
    public CostAccountingView(CostAccountingRepository repository, CostRepository costRepository, OutputRepository outputRepository) {
        GridCrud<CostAccounting> crud = new GridCrud<>(CostAccounting.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        setColumns(crud);
        setFields(costRepository, outputRepository, crud);

        add(crud);
    }

    private void setColumns(GridCrud<CostAccounting> crud) {
        Grid<CostAccounting> grid = crud.getGrid();
        grid.removeAllColumns();
        crud.getGrid().addColumn(costAccounting -> costAccounting.getOutput().getProduct().getTitle()).setHeader("Продукт");
        crud.getGrid().addColumn(costAccounting -> costAccounting.getOutput().getDate()).setHeader("Дата").setSortable(true);
        crud.getGrid().addColumn(costAccounting -> costAccounting.getCost().getName()).setHeader("Наименование расхода");
        crud.getGrid().addColumn(CostAccounting::getAmount).setHeader("Количество");
    }

    private void setFields(CostRepository costRepository,
                           OutputRepository outputRepository,
                           GridCrud<CostAccounting> crud) {
        CrudFormFactory<CostAccounting> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("cost", "amount", "output");
        crudFormFactory.setFieldCreationListener("cost", field -> ((ComboBox<?>) field).setLabel("Типы расходов"));
        crudFormFactory.setFieldCreationListener("amount", field -> ((TextField) field).setLabel("Количество"));
        crudFormFactory.setFieldCreationListener("output", field -> ((ComboBox<?>) field).setLabel("Выпуск продукции"));
        crudFormFactory.setFieldProvider("cost", new ComboBoxProvider<>(
                "Затраты",
                costRepository.findAll(),
                new TextRenderer<>(Cost::getName),
                Cost::getName));
        crudFormFactory.setFieldProvider("output", new ComboBoxProvider<>(
                "Выпуск продукции",
                outputRepository.findByDateAfter(LocalDate.now().minusDays(7)),
                new TextRenderer<>(output -> output.getProduct().getTitle() + " " + output.getDate()),
                output -> output.getProduct().getTitle() + " " + output.getDate()));
    }
}
