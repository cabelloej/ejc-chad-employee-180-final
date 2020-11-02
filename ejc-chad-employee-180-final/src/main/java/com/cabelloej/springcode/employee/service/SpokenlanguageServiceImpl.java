package com.cabelloej.springcode.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.domain.Relative;
import com.cabelloej.springcode.employee.domain.Spokenlanguage;
import com.cabelloej.springcode.employee.repository.EmployeeRepository;
import com.cabelloej.springcode.employee.repository.SpokenlanguageRepository;

@Service
public class SpokenlanguageServiceImpl implements SpokenlanguageService {

	private EmployeeRepository employeeRepository;
	private SpokenlanguageRepository spokenlanguageRepository;
	
	@Autowired
	public SpokenlanguageServiceImpl(EmployeeRepository theEmployeeRepository, SpokenlanguageRepository theLanguageRepository) {
		employeeRepository= theEmployeeRepository;
		spokenlanguageRepository= theLanguageRepository;
	}
	
	// List of Methods for Direct Language Management
	//
	
	@Override
	public List<Spokenlanguage> findAll() {
		return spokenlanguageRepository.findAll();
	}

	@Override
	public Spokenlanguage findById(int theId) {
		Optional<Spokenlanguage> result = spokenlanguageRepository.findById(theId);
		
		Spokenlanguage theSpokenlanguage = null;
		
		if (result.isPresent()) {
			theSpokenlanguage = result.get();
		}
		else {
			// we didn't find the Language
			throw new RuntimeException("Did not find theLanguage id - " + theId);
		}
		
		return theSpokenlanguage;
	}

	@Override
    @Transactional
	public void save(Spokenlanguage theSpokenlanguage) {
		spokenlanguageRepository.save(theSpokenlanguage);
	}

	@Override
    @Transactional
	public void deleteById(int theId) {
		spokenlanguageRepository.deleteById(theId);
	}

}






