package com.example.application.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.OperationAccounting;

public interface OperationAccountingRepository extends JpaRepository<OperationAccounting, Integer> {
  List<OperationAccounting> findAllByDate(LocalDate date);
}
