package com.example.application.data.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.application.data.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutputRepository extends JpaRepository<Output, Integer> {
  List<Output> findByDateAfter(LocalDate localDate);
}