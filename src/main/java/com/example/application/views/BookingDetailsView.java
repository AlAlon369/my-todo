package com.example.application.views;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.Product;
import com.example.application.data.repository.ProductRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.example.application.data.entity.Booking;
import com.example.application.data.entity.BookingProduct;
import com.example.application.data.repository.BookingProductRepository;
import com.example.application.data.repository.BookingRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;


@Route(value = "booking", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class BookingDetailsView extends FormLayout implements HasUrlParameter<Integer> {
  private final GridCrud<BookingProduct> crud;
  private final TextField dateBooking;
  private final TextField clientBooking;
  private final transient BookingRepository bookingRepository;
  private final transient BookingProductRepository repository;

  public BookingDetailsView(BookingProductRepository repository,
                            BookingRepository bookingRepository,
                            ProductRepository productRepository) {
    this.bookingRepository = bookingRepository;
    this.repository = repository;
    crud = new GridCrud<>(BookingProduct.class);
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
    crudFormFactory.setFieldProvider("product",
      new ComboBoxProvider<>(
        "Продукт",
        productRepository.findAll(),
        new TextRenderer<>(Product::getTitle),
        Product::getTitle
      ));

    dateBooking = new TextField();
    dateBooking.setReadOnly(true);
    dateBooking.setLabel("Дата заказа");

    clientBooking = new TextField();
    clientBooking.setReadOnly(true);
    clientBooking.setLabel("Клиент");

    Button back = new Button("Назад", new Icon(VaadinIcon.ARROW_LEFT));
    back.addClickListener(e -> this.getUI().ifPresent(ui -> ui.navigate(BookingView.class)));
    HorizontalLayout detailLayout = new HorizontalLayout(dateBooking, clientBooking);
    VerticalLayout mainLayout = new VerticalLayout(back, detailLayout, crud);
    add(mainLayout);
  }

  @Override
  public void setParameter(BeforeEvent event, Integer bookingId) {
    Booking booking = bookingRepository.findById(bookingId).orElseThrow();
    dateBooking.setValue(booking.getDate().toString());
    clientBooking.setValue(booking.getClient().getCompany());
    crud.setFindAllOperation(() -> repository.findByBooking(booking));
    crud.setAddOperation(entity -> {
      entity.setBooking(booking);
      return repository.save(entity);
    });
    crud.refreshGrid();
  }
}
