package com.example.application.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.application.data.TimeSheetDto;
import com.example.application.data.entity.Employee;
import com.example.application.data.repository.EmployeeRepository;

@Component
public class TimeSheetController {
  private final EmployeeRepository employeeRepository;

  public TimeSheetController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<TimeSheetDto> findAll() {
    List<Employee> byHiredTrue = employeeRepository.findByHiredTrue();
    return List.of(null);
  }

  public TimeSheetDto save(TimeSheetDto timeSheetDto) {
    System.out.println("Сохранили");
    return timeSheetDto;
  }
}
