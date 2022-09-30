package com.example.application.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // List<Employee> findByHiredTrue(); // по этому методу получить всех сотрудников (которые наняты)
}