package com.cabelloej.springcode.employee.service;

import java.util.List;

import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.domain.EmployeeSpokenlanguage;
import com.cabelloej.springcode.employee.domain.Relative;
import com.cabelloej.springcode.employee.domain.Spokenlanguage;
import com.cabelloej.springcode.employee.domain.Sport;

public interface EmployeeService {

	// For Direct Management
	//
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

	public List<Employee> searchBy(String theName);
	
	
	// For Relatives Management
	//
	public Relative findByEmployeeIdAndRelativeId(int employeeId, int relativeId);
	
	public Relative saveEmployeeRelative(Relative relativeToSave);
	
	public void deleteEmployeeRelative(int employeeId, int relativeId);
	
	
	
	// For Liked Sports Management
	//
	public Sport findByEmployeeIdAndSportId(int employeeId, int sportId);
	
    public Sport saveLikedSport(int employeeId, Sport sport);
    
	public void deleteLikedSport(int employeeId, Sport sport);
	
	
	// For Employee's Spokenlanguages Management
    public EmployeeSpokenlanguage findByEmployeeSpokenlanguageId(int employeeSpokenlanguageId);
	
    public EmployeeSpokenlanguage findByEmployeeIdAndSpokenlanguageId(int employeeId, int spokenlanguageId);

    public EmployeeSpokenlanguage saveEmployeeSpokenlanguage(EmployeeSpokenlanguage Employeespokenlanguage);

    public void deleteEmployeeSpokenlanguage(int employeeId, int spokenlanguageId);
	

	
}
