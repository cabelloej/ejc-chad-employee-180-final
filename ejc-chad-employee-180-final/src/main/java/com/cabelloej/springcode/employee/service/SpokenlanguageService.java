package com.cabelloej.springcode.employee.service;

import java.util.List;

import com.cabelloej.springcode.employee.domain.Department;
import com.cabelloej.springcode.employee.domain.Relative;
import com.cabelloej.springcode.employee.domain.Spokenlanguage;

public interface SpokenlanguageService {
	
	// List of Methods for Direct Language Management
	public List<Spokenlanguage> findAll();
	
	public Spokenlanguage findById(int theId);
	
	public void save(Spokenlanguage theSpokenlanguage);
	
	public void deleteById(int theId);

}
