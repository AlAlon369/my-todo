package com.example.application.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.OperationAccounting;
import com.example.application.data.entity.OperationTimeSheet;

public interface OperationTimeSheetRepository extends JpaRepository<OperationTimeSheet, Integer> {
  List<OperationTimeSheet> findByOperationAccounting(OperationAccounting operationAccounting);
}
