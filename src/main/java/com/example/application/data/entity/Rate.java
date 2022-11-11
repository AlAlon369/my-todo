package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rate {
  @Id
  @GeneratedValue
  private Integer id;
  private Integer amount;
  @ManyToOne
  private Operation operation;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Operation getOperation() {
    return operation;
  }

  public void setOperation(Operation operation) {
    this.operation = operation;
  }


  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}
