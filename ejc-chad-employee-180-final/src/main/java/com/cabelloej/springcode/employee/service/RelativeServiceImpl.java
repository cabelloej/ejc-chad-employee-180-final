package com.cabelloej.springcode.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.domain.Relative;
import com.cabelloej.springcode.employee.repository.EmployeeRepository;
import com.cabelloej.springcode.employee.repository.RelativeRepository;

@Service
public class RelativeServiceImpl implements RelativeService {
	
	private RelativeRepository relativeRepository;
	
	@Autowired
	public RelativeServiceImpl(EmployeeRepository theEmployeeRepository, RelativeRepository theRelativeRepository) {
		relativeRepository = theRelativeRepository;
	}


	/////////////////////////////////////////////////////////////////////
	//  Implemented methods for future Macro-Management of relatives   
	//
	@Override
	public List<Relative> macroFindAll() {
		return relativeRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Relative macroFindById(int theId) {
		Optional<Relative> result = relativeRepository.findById(theId);
		
		Relative relative = null;
		
		if (result.isPresent()) {
			relative = result.get();
		}
		else {
			// we didn't find the relative
			throw new RuntimeException("Did not find relative id - " + theId);
		}
		
		return relative;
	}

	@Override
	public void macroSave(Relative theRelative) {
		relativeRepository.save(theRelative);
	}

	@Override
	public void macroDeleteById(int theId) {
		relativeRepository.deleteById(theId);
	}

	@Override
	public List<Relative> macroSearch(String theName) {
		
		List<Relative> results = null;
		
		if (theName != null && (theName.trim().length() > 0)) {
			results = relativeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(theName, theName);
		}
		else {
			results = macroFindAll();
		}
		
		return results;
	}

}






