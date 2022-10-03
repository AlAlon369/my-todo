package com.example.application.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.application.data.TimeSheetDto;

@Component
public class TimeSheetController {
    public List<TimeSheetDto> findAll() {
     TimeSheetDto dto1 = new TimeSheetDto();
     TimeSheetDto dto2 = new TimeSheetDto();
     TimeSheetDto dto3 = new TimeSheetDto();

     dto1.setFio("Иванов Иван");
     dto1.setHoursDay1(5);
     dto1.setHoursDay3(5);
     dto1.setHoursDay2(5);
     dto1.setHoursDay5(5);

      dto2.setFio("Петров Иван");
      dto2.setHoursDay1(6);
      dto2.setHoursDay3(3);
      dto2.setHoursDay2(3);
      dto2.setHoursDay5(7);

      dto3.setFio("Сидоров Иван");
      dto3.setHoursDay1(2);
      dto3.setHoursDay3(4);
      dto3.setHoursDay2(8);
      dto3.setHoursDay5(4);

      return List.of(dto1, dto2, dto3);
    }

    public TimeSheetDto save(TimeSheetDto timeSheetDto) {
      System.out.println("Сохранили");
      return timeSheetDto;
    }
}
