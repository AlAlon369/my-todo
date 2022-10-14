package com.example.application.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {
}