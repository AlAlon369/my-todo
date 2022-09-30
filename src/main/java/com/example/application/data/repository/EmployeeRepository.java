package com.example.application.data.repository;

import com.example.application.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByHiredTrue(); // по этому методу получить всех сотрудников (которые наняты)
}