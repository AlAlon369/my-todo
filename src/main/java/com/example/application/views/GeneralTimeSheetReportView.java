package com.example.application.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.example.application.data.entity.GeneralTimeSheet;
import com.example.application.service.GeneralTimeSheetReportService;
import com.example.application.service.GeneralTimeSheetReport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route(value = "timesheet-report", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class GeneralTimeSheetReportView extends FormLayout {

  public GeneralTimeSheetReportView(GeneralTimeSheetReportService service) {
    Grid<GeneralTimeSheetReport> grid = new Grid<>(GeneralTimeSheetReport.class, false);
    grid.addColumn(generalTimeSheetReport -> generalTimeSheetReport.getEmployee().getLastName()).setHeader("Фамилия");
    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM");
    for (int day = 6; day >= 0; day--) {
      int finalDay = day;
      grid.addColumn(report -> getTimeSheets(report, finalDay)).setHeader(LocalDate.now().minusDays(day).format(pattern));
    }
    List<GeneralTimeSheetReport> reports = service.getReports(7);
    grid.setItems(reports);
    add(grid);
  }

  private static int getTimeSheets(GeneralTimeSheetReport report, int day) {
    LocalDate localDate = LocalDate.now().minusDays(day);
    List<GeneralTimeSheet> sheets = report.getTimeSheets();
    return sheets
      .stream()
      .filter(sheet -> sheet.getDate().isEqual(localDate))
      .findFirst()
      .map(GeneralTimeSheet::getHours)
      .orElse(0);
  }
}
