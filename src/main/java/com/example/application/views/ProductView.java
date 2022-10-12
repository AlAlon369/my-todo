package com.example.application.views;

import com.example.application.data.entity.Product;
import com.example.application.data.repository.ProductRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import javax.annotation.security.RolesAllowed;

@Route(value = "product", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class ProductView extends FormLayout {
    public ProductView(ProductRepository repository) {
        GridCrud<Product> crud = new GridCrud<>(Product.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Product> grid = crud.getGrid();
        grid.getColumnByKey("title").setHeader("Название");
        grid.removeColumnByKey("id");

        CrudFormFactory<Product> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("title");
        crudFormFactory.setFieldCreationListener("title", field -> ((TextField) field).setLabel("Название"));

        add(crud);
    }
}
