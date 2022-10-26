package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.BookingProduct;
import com.example.application.data.repository.BookingProductRepository;
import com.example.application.data.repository.BookingRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "booking", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class BookingProductsView extends FormLayout implements HasUrlParameter<String> {
  private final GridCrud<BookingProduct> crud;
  private final transient BookingRepository bookingRepository;
  private final transient BookingProductRepository repository;

  public BookingProductsView(BookingProductRepository repository, BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
    this.repository = repository;
    crud = new GridCrud<>(BookingProduct.class);
    crud.setAddOperation(repository::save);
    crud.setUpdateOperation(repository::save);
    crud.setDeleteOperation(repository::delete);
    add(crud);
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    Booking booking = bookingRepository.findById(Integer.valueOf(parameter)).orElseThrow();
    crud.setFindAllOperation(() -> repository.findByBooking(booking));
    crud.refreshGrid();
  }
}
