package com.example.application.views;

import com.example.application.data.entity.Operation;
import com.example.application.data.entity.Product;
import com.example.application.data.repository.OperationRepository;
import com.example.application.data.repository.ProductRepository;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import javax.annotation.security.RolesAllowed;

@Route(value = "operation", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OperationView extends FormLayout {
    public OperationView(OperationRepository repository, ProductRepository productRepository) {
        GridCrud<Operation> crud = new GridCrud<>(Operation.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Operation> grid = crud.getGrid();
        grid.getColumnByKey("title").setHeader("Операция");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("product");

        CrudFormFactory<Operation> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("title", "product");
        crudFormFactory.setFieldCreationListener("title", field -> ((TextField) field).setLabel("Операция"));
        crud.getGrid().addColumn(user -> user.getProduct().getTitle()).setHeader("Продукт");
        crudFormFactory.setFieldCreationListener("product", field -> ((ComboBox<?>) field).setLabel("Продукт"));

        crudFormFactory.setFieldProvider("product",
                new ComboBoxProvider<>(
                        "Продукт",
                        productRepository.findAll(),
                        new TextRenderer<>(Product::getTitle),
                        Product::getTitle
                ));
        add(crud);
    }
}
