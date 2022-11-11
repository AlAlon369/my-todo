package com.example.application.views;

import java.math.BigDecimal;
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
        crud.getGrid().addColumn(booking -> booking.getBookingProducts() != null
          ? booking.getBookingProducts().stream()
          .mapToInt(value -> value.getPrice().multiply(BigDecimal.valueOf(value.getQuantity())).intValue())
          .sum()
          : 0).setHeader("Сумма заказа");

        grid.addComponentColumn(booking -> {
          Button detailsButton = new Button("Подробности");
          detailsButton.addClickListener(e ->
            this.getUI().ifPresent(ui ->
              ui.navigate(
                BookingDetailsView.class,
                booking.getId()
              )));
          return detailsButton;
        }).setWidth("150px").setFlexGrow(0);

        GridSortOrder<Booking> order = new GridSortOrder<>(dateColumn, SortDirection.DESCENDING);
        grid.sort(List.of(order));

        CrudFormFactory<Booking> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("client", "date");
        crudFormFactory.setFieldCreationListener("date", field -> ((DatePicker) field).setLabel("Дата"));

        crudFormFactory.setFieldProvider("client",
                new ComboBoxProvider<>(
                        "Клиент",
                        clientRepository.findAll(),
                        new TextRenderer<>(Client::getCompany),
                        Client::getCompany
                ));
        add(crud);
    }
}
