package com.example.application.views;

import com.example.application.data.entity.Order;
import com.example.application.data.repository.ClientRepository;
import com.example.application.data.repository.OrderRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

import javax.annotation.security.RolesAllowed;

@Route(value = "orders", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OrdersView extends FormLayout {
    public OrdersView(OrderRepository repository, ClientRepository clientRepository) {
        GridCrud<Order> crud = new GridCrud<>(Order.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);

        Grid<Order> grid = crud.getGrid();
        grid.getColumnByKey("quality");    // setHeader
        grid.getColumnByKey("capacity");   // setHeader
        grid.getColumnByKey("quantity");   // setHeader
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("client");

        add(crud);
    }
}
