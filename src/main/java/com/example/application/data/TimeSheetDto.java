package com.example.application.data;


import java.util.List;

import com.example.application.data.entity.Employee;
import com.example.application.data.entity.TimeSheet;

public class TimeSheetDto {
  private Employee employee;
  private List<TimeSheet> timeSheets;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public List<TimeSheet> getTimeSheets() {
    return timeSheets;
  }

  public void setTimeSheets(List<TimeSheet> timeSheets) {
    this.timeSheets = timeSheets;
  }
}
