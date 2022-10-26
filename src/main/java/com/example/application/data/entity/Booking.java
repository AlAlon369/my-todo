package com.example.application.data.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDate date;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    private List<BookingProduct> bookingProducts;
    @ManyToOne
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<BookingProduct> getBookingProducts() {
        return bookingProducts;
    }

    public void setBookingProducts(List<BookingProduct> bookingProducts) {
        this.bookingProducts = bookingProducts;
    }
}
