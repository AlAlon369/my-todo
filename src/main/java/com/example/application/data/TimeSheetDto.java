package com.example.application.data;


import com.example.application.data.entity.Employee;
import com.example.application.data.entity.TimeSheet;

public class TimeSheetDto {
  private Employee employee;
  private TimeSheet timeSheetDay1;
  private TimeSheet timeSheetDay2;
  private TimeSheet timeSheetDay3;
  private TimeSheet timeSheetDay4;
  private TimeSheet timeSheetDay5;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public TimeSheet getTimeSheetDay1() {
    return timeSheetDay1;
  }

  public void setTimeSheetDay1(TimeSheet timeSheetDay1) {
    this.timeSheetDay1 = timeSheetDay1;
  }

  public TimeSheet getTimeSheetDay2() {
    return timeSheetDay2;
  }

  public void setTimeSheetDay2(TimeSheet timeSheetDay2) {
    this.timeSheetDay2 = timeSheetDay2;
  }

  public TimeSheet getTimeSheetDay3() {
    return timeSheetDay3;
  }

  public void setTimeSheetDay3(TimeSheet timeSheetDay3) {
    this.timeSheetDay3 = timeSheetDay3;
  }

  public TimeSheet getTimeSheetDay4() {
    return timeSheetDay4;
  }

  public void setTimeSheetDay4(TimeSheet timeSheetDay4) {
    this.timeSheetDay4 = timeSheetDay4;
  }

  public TimeSheet getTimeSheetDay5() {
    return timeSheetDay5;
  }

  public void setTimeSheetDay5(TimeSheet timeSheetDay5) {
    this.timeSheetDay5 = timeSheetDay5;
  }
}
