package com.example.application.data.repository;

import com.example.application.data.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {
}