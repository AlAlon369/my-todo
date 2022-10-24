package com.example.application.data.entity;

import java.util.List;

import javax.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany
    private List<ProductOrder> productOrders;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> orders) {
        this.productOrders = orders;
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
