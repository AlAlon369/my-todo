package com.example.application.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.GeneralTimeSheet;

public interface GeneralTimeSheetRepository extends JpaRepository<GeneralTimeSheet, Integer> {
  List<GeneralTimeSheet> findByDateAfter(LocalDate date);
}