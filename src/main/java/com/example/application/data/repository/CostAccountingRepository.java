package com.example.application.data.repository;

import com.example.application.data.entity.CostAccounting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostAccountingRepository extends JpaRepository<CostAccounting, Integer> {
}