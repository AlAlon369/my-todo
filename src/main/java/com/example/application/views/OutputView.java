package com.example.application.views;

import com.example.application.data.entity.Output;
import com.example.application.data.repository.OutputRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import javax.annotation.security.RolesAllowed;

@Route(value = "output", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OutputView extends FormLayout {
    public OutputView(OutputRepository repository) {
        GridCrud<Output> crud = new GridCrud<>(Output.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Output> grid = crud.getGrid();
        grid.getColumnByKey("date").setHeader("Дата");
        grid.getColumnByKey("amount").setHeader("Количество");
        grid.getColumnByKey("productId").setHeader("Продукт");
        grid.removeColumnByKey("id");

        CrudFormFactory<Output> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("date", "amount", "productId");

        add(crud);
    }
}
