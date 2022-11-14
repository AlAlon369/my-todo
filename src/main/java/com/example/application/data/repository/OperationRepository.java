package com.example.application.data.repository;

import java.util.List;

import com.example.application.data.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
  List<Operation> findAllByOrderByTitleAsc();
}