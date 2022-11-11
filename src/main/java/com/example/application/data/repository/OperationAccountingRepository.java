package com.example.application.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.OperationAccounting;

public interface OperationAccountingRepository extends JpaRepository<OperationAccounting, Integer> {
}
