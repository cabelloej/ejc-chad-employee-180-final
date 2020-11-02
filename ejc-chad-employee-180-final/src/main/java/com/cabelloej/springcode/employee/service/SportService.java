package com.cabelloej.springcode.employee.service;

import java.util.List;

import com.cabelloej.springcode.employee.domain.Sport;

public interface SportService {
	
	public List<Sport> findAll();
	
	public Sport findById(int theId);
	
	public void save(Sport theSporte);
	
	public void deleteById(int theId);
	
}
