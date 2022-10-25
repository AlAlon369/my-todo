package com.example.application.data.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TimeSheet {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer hours;
    private LocalDate date;
    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Operation operation;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
