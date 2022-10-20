package com.example.application.service;

import java.util.List;

import com.example.application.data.entity.Employee;
import com.example.application.data.entity.GeneralTimeSheet;

public class GeneralTimeSheetReport {
  private Employee employee;
  private List<GeneralTimeSheet> timeSheets;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public List<GeneralTimeSheet> getTimeSheets() {
    return timeSheets;
  }

  public void setTimeSheets(List<GeneralTimeSheet> timeSheets) {
    this.timeSheets = timeSheets;
  }
}
