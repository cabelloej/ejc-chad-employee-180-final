package com.cabelloej.springcode.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabelloej.springcode.employee.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

		
}
