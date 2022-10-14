package com.example.application.views;

import com.example.application.data.entity.Cost;
import com.example.application.data.entity.CostAccounting;
import com.example.application.data.entity.Output;
import com.example.application.data.repository.CostAccountingRepository;
import com.example.application.data.repository.CostRepository;
import com.example.application.data.repository.OutputRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import javax.annotation.security.RolesAllowed;

@Route(value = "cost_accounting", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class CostAccountingView extends FormLayout {
    public CostAccountingView(CostAccountingRepository repository, CostRepository costRepository, OutputRepository outputRepository) {
        GridCrud<CostAccounting> crud = new GridCrud<>(CostAccounting.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<CostAccounting> grid = crud.getGrid();
        grid.getColumnByKey("amount").setHeader("Количество");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("cost");
        grid.removeColumnByKey("output");
        crud.getGrid().addColumn(costAccounting -> costAccounting.getOutput().getProduct().getTitle()).setHeader("Продукт");
        crud.getGrid().addColumn(costAccounting -> costAccounting.getCost().getName()).setHeader("Наименование расхода");

        CrudFormFactory<CostAccounting> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("cost", "amount", "output");
        crudFormFactory.setFieldProvider("cost", new ComboBoxProvider<>("Затраты", costRepository.findAll(),
                new TextRenderer<>(Cost::getName), Cost::getName));
        crudFormFactory.setFieldProvider("output", new ComboBoxProvider<>("Выпуск продукции", outputRepository.findAll(),
                new TextRenderer<>(Output::getName), Output::getName));
        add(crud);
    }
}
