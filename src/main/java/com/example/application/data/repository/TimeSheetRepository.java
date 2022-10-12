package com.example.application.data.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.application.data.entity.Product;
import com.example.application.data.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {
  List<TimeSheet> findByDateAfterAndProductEquals(LocalDate date, Product product);
}