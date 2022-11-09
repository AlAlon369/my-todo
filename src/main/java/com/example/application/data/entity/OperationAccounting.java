package com.example.application.data.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class OperationAccounting {
  @Id
  @GeneratedValue
  private Integer id;
  private LocalDate date;
  private Integer plan;
  private Integer fact;
  @ManyToOne
  private Operation operation;
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "operation_accounting_id")
  private List<OperationTimeSheet> timeSheets;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPlan() {
    return plan;
  }

  public void setPlan(Integer plan) {
    this.plan = plan;
  }

  public Integer getFact() {
    return fact;
  }

  public void setFact(Integer fact) {
    this.fact = fact;
  }

  public Operation getOperation() {
    return operation;
  }

  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  public List<OperationTimeSheet> getTimeSheets() {
    return timeSheets;
  }

  public void setTimeSheets(List<OperationTimeSheet> timeSheets) {
    this.timeSheets = timeSheets;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
