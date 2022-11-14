package com.example.application.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.Rate;

public interface RateRepository extends JpaRepository<Rate, Integer> {
  List<Rate> findAllByOrderByOperationAscAmountAsc();
}