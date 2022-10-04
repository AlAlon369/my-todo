package com.example.application.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.application.data.TimeSheetDto;
import com.example.application.data.entity.Employee;
import com.example.application.data.entity.TimeSheet;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.TimeSheetRepository;

@Component
public class TimeSheetController {
  private final EmployeeRepository employeeRepository;
  private final TimeSheetRepository timeSheetRepository;

  public TimeSheetController(EmployeeRepository employeeRepository,
                             TimeSheetRepository timeSheetRepository) {
    this.employeeRepository = employeeRepository;
    this.timeSheetRepository = timeSheetRepository;
  }

  public List<TimeSheetDto> findAll() {
    List<Employee> hiredEmployees = employeeRepository.findByHiredTrue();
    List<TimeSheet> allTimeSheets = timeSheetRepository.findByDateAfter(LocalDate.now().minusDays(5));

    List<TimeSheetDto> list = new ArrayList<>();
    for (Employee hiredEmployee : hiredEmployees) {
      TimeSheetDto timeSheetDto = new TimeSheetDto();
      timeSheetDto.setFio(hiredEmployee.getFirstName() + " " + hiredEmployee.getLastName());
      setHoursForEmployee(hiredEmployee, timeSheetDto, allTimeSheets);
      list.add(timeSheetDto);
    }

    return list;
  }

  private void setHoursForEmployee(Employee hiredEmployee, TimeSheetDto timeSheetDto, List<TimeSheet> allTimeSheets) {
    for (TimeSheet sheet : allTimeSheets) {
      // TimeSheet относится именно к этому hiredEmployee
      if (sheet.getEmployee().getId().equals(hiredEmployee.getId())) {
        LocalDate date = sheet.getDate();
        Integer hours = sheet.getHours();
        long between = ChronoUnit.DAYS.between(date, LocalDate.now());
        if (between == 0) {
          timeSheetDto.setHoursDay5(hours);
        } else if (between == 1) {
          timeSheetDto.setHoursDay4(hours);
        } else if (between == 2) {
          timeSheetDto.setHoursDay3(hours);
        } else if (between == 3) {
          timeSheetDto.setHoursDay2(hours);
        } else if (between == 4) {
          timeSheetDto.setHoursDay1(hours);
        }
      }
    }
  }

  public TimeSheetDto save(TimeSheetDto timeSheetDto) {
    System.out.println("Сохранили");
    return timeSheetDto;
  }
}
