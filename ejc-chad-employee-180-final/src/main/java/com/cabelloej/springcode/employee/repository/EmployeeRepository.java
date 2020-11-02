package com.cabelloej.springcode.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabelloej.springcode.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// add a method to sort by last name
	public List<Employee> findAllByOrderByLastNameAsc();
	
	// search by name
	public List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);

}
