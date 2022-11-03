package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Client;
import com.example.application.data.repository.BookingRepository;
import com.example.application.data.repository.ClientRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "booking", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class BookingView extends FormLayout {
    public BookingView(BookingRepository bookingRepository, ClientRepository clientRepository) {
        GridCrud<Booking> crud = new GridCrud<>(Booking.class);
        crud.setFindAllOperation(bookingRepository::findAll);
        crud.setAddOperation(bookingRepository::save);
        crud.setUpdateOperation(bookingRepository::save);
        crud.setDeleteOperation(bookingRepository::delete);

        Grid<Booking> grid = crud.getGrid();
        Grid.Column<Booking> dateColumn = grid.getColumnByKey("date").setHeader("Дата");
        grid.getColumnByKey("client").setHeader("Клиент");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("bookingProducts");
        grid.removeColumnByKey("client");
        grid.addColumn(booking -> booking.getClient() != null ? booking.getClient().getCompany() : null)
          .setHeader("Клиент");
        GridSortOrder<Booking> order = new GridSortOrder<>(dateColumn, SortDirection.DESCENDING);
        grid.sort(List.of(order));

        CrudFormFactory<Booking> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("client", "date");
        crudFormFactory.setFieldCreationListener("date", field -> ((DatePicker) field).setLabel("Дата"));

        Button button = new Button("Подробности заказа");
        button.setEnabled(false);
        button.addClickListener(e ->
          button.getUI().ifPresent(ui ->
            ui.navigate(
              BookingProductsView.class,
              crud.getGrid().getSelectedItems().iterator().next().getId()
            )
          )
        );
        crud.getGrid().addItemClickListener(event -> button.setEnabled(true));

        crudFormFactory.setFieldProvider("client",
                new ComboBoxProvider<>(
                        "Клиент",
                        clientRepository.findAll(),
                        new TextRenderer<>(Client::getCompany),
                        Client::getCompany
                ));
        VerticalLayout verticalLayout = new VerticalLayout(button, crud);
        add(verticalLayout);
    }
}
