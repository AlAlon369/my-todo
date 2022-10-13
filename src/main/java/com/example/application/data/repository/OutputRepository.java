package com.example.application.data.repository;

import com.example.application.data.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutputRepository extends JpaRepository<Output, Integer> {
}