package com.example.application.data.repository;

import com.example.application.data.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost, Integer> {
}