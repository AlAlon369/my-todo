package com.example.application.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.application.data.entity.Product;
import com.example.application.data.repository.ProductRepository;
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
  private final ProductRepository productRepository;

  public TimeSheetController(EmployeeRepository employeeRepository,
                             TimeSheetRepository timeSheetRepository,
                             ProductRepository productRepository) {
    this.employeeRepository = employeeRepository;
    this.timeSheetRepository = timeSheetRepository;
    this.productRepository = productRepository;
  }

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public List<TimeSheetDto> findAll() {
    List<Employee> hiredEmployees = employeeRepository.findByHiredTrue();
    List<TimeSheet> allTimeSheets = timeSheetRepository.findByDateAfter(LocalDate.now().minusDays(5));
    Map<Integer, List<TimeSheet>> map = collectTimeSheets(allTimeSheets);
    List<TimeSheetDto> list = new ArrayList<>();
    for (Employee hiredEmployee : hiredEmployees) {
      TimeSheetDto timeSheetDto = new TimeSheetDto();
      timeSheetDto.setEmployee(hiredEmployee);
      setHoursForEmployee(hiredEmployee, timeSheetDto, map);
      list.add(timeSheetDto);
    }

    return list;
  }

  public void save(TimeSheet timeSheet) {
    timeSheetRepository.save(timeSheet);
  }

  private Map<Integer, List<TimeSheet>> collectTimeSheets(List<TimeSheet> allTimeSheets) {
    return allTimeSheets.stream()
      .collect(Collectors.toMap(
          timeSheet -> timeSheet.getEmployee().getId(),
          List::of,
          (timeSheets1, timeSheets2) -> {
            List<TimeSheet> tempList = new ArrayList<>();
            tempList.addAll(timeSheets1);
            tempList.addAll(timeSheets2);
            return tempList;
          }
        )
      );
  }

  private void setHoursForEmployee(Employee hiredEmployee, TimeSheetDto timeSheetDto, Map<Integer, List<TimeSheet>> map) {
    List<TimeSheet> timeSheets = map.getOrDefault(hiredEmployee.getId(), new ArrayList<>());
    for (TimeSheet sheet : timeSheets) {
      long between = ChronoUnit.DAYS.between(sheet.getDate(), LocalDate.now());
      if (between == 0) {
        timeSheetDto.setTimeSheetDay5(sheet);
      } else if (between == 1) {
        timeSheetDto.setTimeSheetDay4(sheet);
      } else if (between == 2) {
        timeSheetDto.setTimeSheetDay3(sheet);
      } else if (between == 3) {
        timeSheetDto.setTimeSheetDay2(sheet);
      } else if (between == 4) {
        timeSheetDto.setTimeSheetDay1(sheet);
      }
    }
  }
}
