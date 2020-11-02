package com.cabelloej.springcode.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabelloej.springcode.employee.domain.Sport;
import com.cabelloej.springcode.employee.repository.SportRepository;

@Service
public class SportServiceImpl implements SportService {

	private SportRepository sportRepository;
	
	@Autowired
	public SportServiceImpl(EmployeeService employeeService, SportRepository theSportRepository) {
		sportRepository= theSportRepository;
	}
	
	@Override
	public List<Sport> findAll() {
		return sportRepository.findAll();
	}

	@Override
	public Sport findById(int theId) {
		Optional<Sport> result = sportRepository.findById(theId);
		
		Sport theSport = null;
		
		if (result.isPresent()) {
			theSport = result.get();
		}
		else {
			// we didn't find the Sport
			throw new RuntimeException("Did not find the Sport id - " + theId);
		}
		
		return theSport;
	}

	@Override
    @Transactional
	public void save(Sport theSport) {
		sportRepository.save(theSport);
	}

	@Override
    @Transactional
	public void deleteById(int theId) {
		sportRepository.deleteById(theId);
	}

}






