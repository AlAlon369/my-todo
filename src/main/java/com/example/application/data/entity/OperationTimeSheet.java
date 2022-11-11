package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OperationTimeSheet {
  @Id
  @GeneratedValue
  private Integer id;
  private Integer hours;
  @ManyToOne
  private Employee employee;
  @ManyToOne
  private OperationAccounting operationAccounting;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getHours() {
    return hours;
  }

  public void setHours(Integer hours) {
    this.hours = hours;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public OperationAccounting getOperationAccounting() {
    return operationAccounting;
  }

  public void setOperationAccounting(OperationAccounting operationAccounting) {
    this.operationAccounting = operationAccounting;
  }
}
