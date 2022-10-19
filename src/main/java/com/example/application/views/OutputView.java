package com.example.application.views;

import com.example.application.data.entity.Output;
import com.example.application.data.entity.Product;
import com.example.application.data.repository.OutputRepository;
import com.example.application.data.repository.ProductRepository;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import javax.annotation.security.RolesAllowed;

@Route(value = "output", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OutputView extends FormLayout {
    public OutputView(OutputRepository repository, ProductRepository productRepository) {
        GridCrud<Output> crud = new GridCrud<>(Output.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Output> grid = crud.getGrid();
        grid.getColumnByKey("date").setHeader("Дата");
        grid.getColumnByKey("amount").setHeader("Количество");
        crud.getGrid().addColumn(user -> user.getProduct().getTitle()).setHeader("Продукт");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("product");

        CrudFormFactory<Output> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("date", "amount", "product");
        crudFormFactory.setFieldCreationListener("amount", field -> ((TextField) field).setLabel("Количество"));
        crudFormFactory.setFieldCreationListener("date", field -> ((DatePicker) field).setLabel("Дата"));
        crudFormFactory.setFieldCreationListener("product", field -> ((ComboBox<?>) field).setLabel("Продукт"));
        crudFormFactory.setFieldProvider("product",
                new ComboBoxProvider<>("Продукт", productRepository.findAll(), new TextRenderer<>(Product::getTitle), Product::getTitle));

        add(crud);
    }
}
