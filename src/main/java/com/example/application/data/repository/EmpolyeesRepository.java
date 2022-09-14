package com.example.application.data.repository;

import com.example.application.data.entity.Empolyees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpolyeesRepository extends JpaRepository<Empolyees, Integer> {
}