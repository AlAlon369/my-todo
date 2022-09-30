package com.example.application.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.application.data.TimeSheetDto;
import com.example.application.data.entity.Employee;
import com.example.application.data.entity.TimeSheet;

@Component
public class TimeSheetController {
    public List<TimeSheetDto> findAll() {
      Employee employee = new Employee();
      employee.setId(1);
      employee.setFirstName("Иван");
      employee.setLastName("Иванов");

      Employee employee2 = new Employee();
      employee2.setId(2);
      employee2.setFirstName("Петр");
      employee2.setLastName("Петров");

      TimeSheet timeSheet = new TimeSheet();
      timeSheet.setDate(LocalDate.now());
      timeSheet.setHours(8);
      timeSheet.setId(1);
      TimeSheet timeSheet2 = new TimeSheet();
      timeSheet2.setDate(LocalDate.now());
      timeSheet2.setHours(7);
      timeSheet2.setId(2);

      TimeSheetDto timeSheetDto = new TimeSheetDto();
      timeSheetDto.setEmployee(employee);
      timeSheetDto.setTimeSheets(List.of(timeSheet, timeSheet2));

      TimeSheetDto timeSheetDto2 = new TimeSheetDto();
      timeSheetDto2.setEmployee(employee2);
      timeSheetDto2.setTimeSheets(List.of(timeSheet, timeSheet2));

      return List.of(timeSheetDto, timeSheetDto2);
    }

    public TimeSheetDto save(TimeSheetDto timeSheetDto) {
      System.out.println("Сохранили");
      return timeSheetDto;
    }
}
