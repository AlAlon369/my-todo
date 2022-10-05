package com.example.application.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    Map<Integer, List<TimeSheet>> map = collectTimeSheets(allTimeSheets);
    List<TimeSheetDto> list = new ArrayList<>();
    for (Employee hiredEmployee : hiredEmployees) {
      TimeSheetDto timeSheetDto = new TimeSheetDto();
      timeSheetDto.setFio(hiredEmployee.getFirstName() + " " + hiredEmployee.getLastName());
      timeSheetDto.setEmployeeId(hiredEmployee.getId());
      setHoursForEmployee(hiredEmployee, timeSheetDto, map);
      list.add(timeSheetDto);
    }

    return list;
  }

  private Map<Integer, List<TimeSheet>> collectTimeSheets(List<TimeSheet> allTimeSheets) {
    // Дай мне List по id (Integer), например по id 9
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
      Integer hours = sheet.getHours();
      long between = ChronoUnit.DAYS.between(sheet.getDate(), LocalDate.now());
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

  public TimeSheetDto save(TimeSheetDto timeSheetDto) {
    int employeeId = timeSheetDto.getEmployeeId();
    Employee employee = employeeRepository.findById(employeeId).orElseThrow();
    int hoursDay1 = timeSheetDto.getHoursDay1();
    int hoursDay2 = timeSheetDto.getHoursDay2();
    int hoursDay3 = timeSheetDto.getHoursDay3();
    int hoursDay4 = timeSheetDto.getHoursDay4();
    int hoursDay5 = timeSheetDto.getHoursDay5();
    List<Integer> listOfHours = List.of(hoursDay5, hoursDay4, hoursDay3, hoursDay2, hoursDay1);
    for (int day = 4; day >= 0; day--) {
      TimeSheet timeSheet = new TimeSheet();
      timeSheet.setEmployee(employee);
      timeSheet.setHours(listOfHours.get(day));
      timeSheet.setDate(LocalDate.now().minusDays(day));
      timeSheetRepository.save(timeSheet);
    }
    return timeSheetDto;
  }
}
