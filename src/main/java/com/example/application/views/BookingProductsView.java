package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.BookingProduct;
import com.example.application.data.repository.BookingProductRepository;
import com.example.application.data.repository.BookingRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.form.CrudFormFactory;


@Route(value = "booking", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class BookingProductsView extends FormLayout implements HasUrlParameter<Integer> {
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

    Grid<BookingProduct> grid = crud.getGrid();
    grid.removeColumnByKey("id");
    grid.removeColumnByKey("product");
    grid.removeColumnByKey("booking");
    Grid.Column<BookingProduct> price = grid.getColumnByKey("price").setHeader("Цена");
    Grid.Column<BookingProduct> quantity = grid.getColumnByKey("quantity").setHeader("Количество");
    Grid.Column<BookingProduct> product = grid.addColumn(
        booking -> booking.getProduct() != null ? booking.getProduct().getTitle() : null)
      .setHeader("Продукт");
    grid.setColumnOrder(List.of(product, quantity, price));

    CrudFormFactory<BookingProduct> crudFormFactory = crud.getCrudFormFactory();
    crudFormFactory.setFieldCreationListener("price", field -> ((TextField) field).setLabel("Цена"));
    crudFormFactory.setFieldCreationListener("quantity", field -> ((TextField) field).setLabel("Количество"));
    crudFormFactory.setFieldCreationListener("id", field -> ((TextField) field).setVisible(false));

    add(crud);
  }

  @Override
  public void setParameter(BeforeEvent event, Integer bookingId) {
    Booking booking = bookingRepository.findById(bookingId).orElseThrow();
    crud.setFindAllOperation(() -> repository.findByBooking(booking));
    crud.refreshGrid();
  }
}
