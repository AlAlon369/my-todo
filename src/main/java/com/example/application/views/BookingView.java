package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.application.data.entity.Booking;
import com.example.application.data.repository.BookingRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.Route;

@Route(value = "booking", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class BookingView extends FormLayout {
    public BookingView(BookingRepository repository ) {
        GridCrud<Booking> crud = new GridCrud<>(Booking.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);
        add(crud);
    }
}
