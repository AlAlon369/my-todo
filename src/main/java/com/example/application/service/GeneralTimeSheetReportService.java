package com.example.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.application.data.entity.Employee;
import com.example.application.data.entity.GeneralTimeSheet;
import com.example.application.data.repository.EmployeeRepository;
import com.example.application.data.repository.GeneralTimeSheetRepository;

@Service
public class GeneralTimeSheetReportService {

  private final GeneralTimeSheetRepository timeSheetRepository;
  private final EmployeeRepository employeeRepository;

  public GeneralTimeSheetReportService(GeneralTimeSheetRepository timeSheetRepository, EmployeeRepository employeeRepository) {
    this.timeSheetRepository = timeSheetRepository;
    this.employeeRepository = employeeRepository;
  }

  public List<GeneralTimeSheetReport> getReports(int days) {
    List<Employee> hiredEmployees = employeeRepository.findByHiredTrue();
    List<GeneralTimeSheet> timeSheets = timeSheetRepository.findByDateAfter(LocalDate.now().minusDays(days));
    Map<Integer, List<GeneralTimeSheet>> timeSheetsMap = collectTimeSheets(timeSheets);
    return hiredEmployees.stream()
      .map(employee -> {
        GeneralTimeSheetReport generalTimeSheetReport = new GeneralTimeSheetReport();
        generalTimeSheetReport.setEmployee(employee);
        List<GeneralTimeSheet> generalTimeSheets = timeSheetsMap.getOrDefault(employee.getId(), Collections.emptyList());
        generalTimeSheetReport.setTimeSheets(generalTimeSheets);
        return generalTimeSheetReport;
      }).collect(Collectors.toList());
  }

  private Map<Integer, List<GeneralTimeSheet>> collectTimeSheets(List<GeneralTimeSheet> allTimeSheets) {
    return allTimeSheets.stream()
      .collect(Collectors.toMap(
          timeSheet -> timeSheet.getEmployee().getId(),
          List::of,
          (timeSheets1, timeSheets2) -> {
            List<GeneralTimeSheet> tempList = new ArrayList<>();
            tempList.addAll(timeSheets1);
            tempList.addAll(timeSheets2);
            return tempList;
          }
        )
      );
  }
}
