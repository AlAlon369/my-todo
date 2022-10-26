package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.application.data.entity.Booking;
import com.example.application.data.repository.BookingRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "booking", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class BookingView extends FormLayout {
    public BookingView(BookingRepository bookingRepository) {
        GridCrud<Booking> crud = new GridCrud<>(Booking.class);
        crud.setFindAllOperation(bookingRepository::findAll);
        crud.setAddOperation(bookingRepository::save);
        crud.setUpdateOperation(bookingRepository::save);
        crud.setDeleteOperation(bookingRepository::delete);

        Button button = new Button("Подробности заказа");
        button.addClickListener(e ->
          button.getUI().ifPresent(ui ->
            ui.navigate(
              BookingProductsView.class,
              String.valueOf(crud.getGrid().getSelectedItems().iterator().next().getId())
            )
          )
        );

        VerticalLayout verticalLayout = new VerticalLayout(button, crud);
        add(verticalLayout);
    }
}
