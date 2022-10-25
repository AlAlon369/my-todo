package com.example.application.data.entity;

import java.util.List;

import javax.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany
    @JoinColumn(name = "booking_id")
    private List<BookingProduct> bookingProducts;
    @ManyToOne
    private Client client;

    public List<BookingProduct> getProductOrders() {
        return bookingProducts;
    }

    public void setProductOrders(List<BookingProduct> orders) {
        this.bookingProducts = orders;
    }

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
}
