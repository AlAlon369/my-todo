package com.example.application.data.entity;

import javax.persistence.*;

@Entity
public class CostAccounting {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer amount;
    @OneToOne
    @JoinColumn(name = "cost_id", nullable = false)
    private Cost cost;

    @ManyToOne
    @JoinColumn(name = "output_id", nullable = false)
    private Output output;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;

    }
}
