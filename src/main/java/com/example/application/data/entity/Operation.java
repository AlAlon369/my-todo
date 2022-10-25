package com.example.application.data.entity;

import javax.persistence.*;

@Entity
public class Operation {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;

    @ManyToOne
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
