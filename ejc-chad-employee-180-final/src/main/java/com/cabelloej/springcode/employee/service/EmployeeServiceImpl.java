package com.cabelloej.springcode.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.domain.EmployeeSpokenlanguage;
import com.cabelloej.springcode.employee.domain.Relative;
import com.cabelloej.springcode.employee.domain.Spokenlanguage;
import com.cabelloej.springcode.employee.domain.Sport;
import com.cabelloej.springcode.employee.repository.EmployeeRepository;
import com.cabelloej.springcode.employee.repository.EmployeeSpokenlanguageRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private EmployeeSpokenlanguageRepository employeeSpokenlaguageRepository;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository, EmployeeSpokenlanguageRepository tempEmployeeSpokenlaguageRepository) {
		
		employeeRepository= theEmployeeRepository;
		employeeSpokenlaguageRepository= tempEmployeeSpokenlaguageRepository;
	}


	////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods to manage employees 
	//

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAllByOrderByLastNameAsc();
	}

	
	@Override
	public Employee findById(int theId) {
		Optional<Employee> result = employeeRepository.findById(theId);

		Employee theEmployee = null;

		if (result.isPresent()) {
			theEmployee= result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("cabelloej - EmployeeService.findById: Did not find employee id - " + theId);
		}

		return theEmployee;
	}

	
	@Override
	@Transactional
	public void save(Employee theEmployee) {
		employeeRepository.save(theEmployee);
	}

	
	@Override
	@Transactional
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}

	
	@Override
	public List<Employee> searchBy(String theName) {

		List<Employee> results = null;

		if (theName != null && (theName.trim().length() > 0)) {
			results = employeeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(theName, theName);
		}
		else {
			results = findAll();
		}

		return results;
	}




	////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods to manage employees/relatives 
	//

	@Override
	public Relative findByEmployeeIdAndRelativeId(int employeeId, int relativeId) {

		// Get Employee
		//
		Employee employee= findById(employeeId);
		if (employee== null) {

			throw new RuntimeException("cabelloej - EmployeeService.findByEmployeeIdAndRelativeId:  Did not find employee id - " + employeeId);
		}

		// Get Relative
		//
		Relative relative= null;
		List<Relative> relatives= employee.getRelatives();
		for(Relative tempRelative : relatives) { 
			if(tempRelative.getId()==relativeId) { 
				relative= tempRelative;
			}
		}
		if (relative== null) {
			// we didn't find the relative
			throw new RuntimeException("cabelloej - EmployeeService.findByEmployeeIdAndRelativeId:  Did not find relative id - " + relativeId);
		}

		return relative;
	}


	@Override
	@Transactional
	public Relative saveEmployeeRelative(Relative relativeToSave) {

		Employee employeeToSave= findById(relativeToSave.getEmployee().getId());
		if (employeeToSave== null) {
			// we didn't find the employee
			throw new RuntimeException("cabelloej - EmployeeService.saveEmployeeRelative: Did not find employee of relative to be saved. Employee id - " + relativeToSave.getEmployee().getId());
		}

		// Get Relative from the Employee's List (if exist)
		//
		// Not the best way to determine if the relative exists in the list... should use stream()
		//
		Relative relativeToSaveInList=null;
		List<Relative> relatives= employeeToSave.getRelatives();
		for(Relative tempRelative : relatives) { 
			if(tempRelative.getId()==relativeToSave.getId()) { 
				// Should we Update here or can we get a handle and update later?
				relativeToSaveInList= tempRelative;
			}
		}

		if (relativeToSaveInList== null) {
			// Relative is not present in List. Must be added
			relativeToSave.setEmployee(employeeToSave);
			employeeToSave.addRelative(relativeToSave);

		}else {
			// Relative is present in List. Must be updated
			// Should we Update here or in the line of code where we found it?
			relativeToSaveInList.setFirstName(relativeToSave.getFirstName());
			relativeToSaveInList.setLastName(relativeToSave.getLastName());
			relativeToSaveInList.setRelation(relativeToSave.getRelation());
			relativeToSaveInList.setGender(relativeToSave.getGender());
			relativeToSaveInList.setBirthDate(relativeToSave.getBirthDate());
			relativeToSaveInList.setHeight(relativeToSave.getHeight());
			relativeToSaveInList.setWeight(relativeToSave.getWeight());
			relativeToSaveInList.setWristSize(relativeToSave.getWristSize());
			relativeToSaveInList.setFavoriteColor(relativeToSave.getFavoriteColor());
			relativeToSaveInList.setSupportDeadline(relativeToSave.getSupportDeadline());
		}


		// Save Employee and his/her Relatives
		//
		Employee savedEmployee= employeeRepository.save(employeeToSave);

		Relative relativeSaved= relativeToSave;

		return relativeSaved;
	}


	@Override
	@Transactional
	public void deleteEmployeeRelative(int employeeId, int relativeId) {

		// Get Employee
		//
		Employee employee= findById(employeeId);
		if (employee== null) {
			// we didn't find the employee
			throw new RuntimeException("cabelloej - EmployeeService.deleteEmployeeRelative:  Did not find employee of relative to be deleted. Employee id - " + employeeId);
		}

		// Get Relative to delete
		//
		Relative relative= null;
		List<Relative> relatives= employee.getRelatives();
		for(Relative tempRelative : relatives) { 
			if(tempRelative.getId()==relativeId) { 
				relative= tempRelative;
			}
		}
		if (relative== null) {
			// we didn't find the relative
			throw new RuntimeException("cabelloej - EmployeeService.deleteEmployeeRelative: Did not find relative to be deleted. Relative id - " + relativeId);
		}else {
			// decouple the relative from the employee
			relative.setEmployee(null);
			// remove the relative from the List
			employee.getRelatives().remove(relative);
			// save the updated Employee 
			employeeRepository.save(employee);
		}
	}



	////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods to manage liked sports
	//

	@Override
	public Sport findByEmployeeIdAndSportId(int employeeId, int sportId) {

		// Get Employee
		Employee employee= findById(employeeId);
		if (employee==null) {

			throw new RuntimeException("EmployeeService.findByEmployeeIdAndSportId: Did not find employee id - " + employeeId);
		}

		// Get Sport. Not the best way to get a relative... should use stream()
		Sport likedport= null;
		List<Sport> likedSports= employee.getLikedSports();
		for(Sport tempSport : likedSports) { 
			if(tempSport.getId()==sportId) { 
				likedport= tempSport;
			}
		}

		return likedport;
	}


	@Override
	@Transactional
	public Sport saveLikedSport(int employeeId, Sport likedSport) {

		// Get Employee
		Employee employeeToSave= findById(employeeId);
		if (employeeToSave==null) {

			throw new RuntimeException("EmployeeService.saveLikedSport: Did not find employee id - " + employeeId);
		}

		// Get likedSport from the Employee's List (if exist)
		Sport sportToSaveInList= findByEmployeeIdAndSportId(employeeId, likedSport.getId());

		if (sportToSaveInList== null) {

			employeeToSave.addSport(likedSport);
		}

		// Save Employee and liked sport
		employeeRepository.save(employeeToSave);


		Sport likedSportSaved= likedSport;
		return likedSportSaved;

	}


	@Override
	@Transactional
	public void deleteLikedSport(int employeeId, Sport likedSport) {

		// Get Employee
		Employee employeeToSave= findById(employeeId);
		if (employeeToSave==null) {

			throw new RuntimeException("EmployeeService.deleteLikedSport: Did not find employee id - " + employeeId);
		}

		// Get likedSport from the Employee's List (if exist)
		Sport sportToDeleteInList= findByEmployeeIdAndSportId(employeeId, likedSport.getId());


		if (sportToDeleteInList== null) {
			// we didn't find the sport
			throw new RuntimeException("EmployeeService.deleteLikedSport: Did not find liked sport to be deleted. Sport id - " + likedSport.getId());
		}else {
			// remove the liked sport from the List
			employeeToSave.getLikedSports().remove(likedSport);
			// save the updated Employee 
			employeeRepository.save(employeeToSave);
		}

	}



	
	////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods to manage Employee's Spokenlanguages
	//
	
	@Override
	public EmployeeSpokenlanguage findByEmployeeSpokenlanguageId(int id) {
		EmployeeSpokenlanguage employeeSpokenlanguage;
		Optional<EmployeeSpokenlanguage> employeeSpokenlanguageOptional= employeeSpokenlaguageRepository.findById(id);
		if (employeeSpokenlanguageOptional.isPresent()){
			employeeSpokenlanguage= employeeSpokenlanguageOptional.get();
		}else {
			throw new RuntimeException("EmployeeService.findByEmployeeSpokenlanguageId: Did not find employeeSpokenlanguage id - " + id);
		}
	    return employeeSpokenlanguage;
	}

	
	
	
	@Override
	public EmployeeSpokenlanguage findByEmployeeIdAndSpokenlanguageId(int employeeId, int id) {

		// Get Employee
		Employee employee= findById(employeeId);
		if (employee==null) {

			throw new RuntimeException("EmployeeService.findByEmployeeIdAndSpokenlanguageId: Did not find employee id - " + employeeId);
		}
 		
		// Get EmployeeSpokenlanguage. Not the best way... should use stream()
		EmployeeSpokenlanguage employeeSpokenlanguage= null;
		
		List<EmployeeSpokenlanguage> employeeSpokenlanguages= employee.getEmployeeSpokenlanguages();
		for(EmployeeSpokenlanguage tempEmployeeSpokenlanguage : employeeSpokenlanguages) { 

			if(tempEmployeeSpokenlanguage.getSpokenlanguage().getId()== id) { 

				employeeSpokenlanguage= tempEmployeeSpokenlanguage;
			 }
		}
		if (employeeSpokenlanguage==null) {

			throw new RuntimeException("EmployeeService.findByEmployeeIdAndSpokenlanguageId: Did not find employee Spokenlanguage id - " + id);
		}
		
        return employeeSpokenlanguage;
	}

    
   	@Override
	@Transactional
	public EmployeeSpokenlanguage saveEmployeeSpokenlanguage(EmployeeSpokenlanguage employeeSpokenlanguage) {
 		// TEST
		//
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: starting .... Employee Id  : "+employeeSpokenlanguage.getEmployee().getId());
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: starting .... Employee name: "+employeeSpokenlanguage.getEmployee().getFirstName());
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: starting .... Language Id  : "+employeeSpokenlanguage.getSpokenlanguage().getId());
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: starting .... Years        : "+employeeSpokenlanguage.getYearsExperience());
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: starting .... Proficiency  : "+employeeSpokenlanguage.getProficiency());
		
		
		
		// Get Employee
		Employee employeeToSave= findById(employeeSpokenlanguage.getEmployee().getId());
		if (employeeToSave==null) {

			throw new RuntimeException("EmployeeService.saveEmployeeSpokenlanguage: Did not find employee id - " + employeeSpokenlanguage.getEmployee().getId());
		}


		
		// TEST
		//
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: Employee to save Id  : "+employeeToSave.getId());
		
		
		
		// Determine if employeeSpokenlanguage is already in Employee's List (create? or update?)
		EmployeeSpokenlanguage employeeSpokenlanguageToSaveInList= null;
		List<EmployeeSpokenlanguage> employeeSpokenlanguages= employeeToSave.getEmployeeSpokenlanguages();
		for(EmployeeSpokenlanguage tempEmployeeSpokenlanguage : employeeSpokenlanguages) { 
			
			 if(tempEmployeeSpokenlanguage.getSpokenlanguage().getId()== employeeSpokenlanguage.getSpokenlanguage().getId()) { 
				 
				 employeeSpokenlanguageToSaveInList= tempEmployeeSpokenlanguage;
			 }
		}
		
		if (employeeSpokenlanguageToSaveInList== null) {
			// TEST
			//
			System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: EmployeeSpokenlanguage was NOT found ");
		
			
			// employeeSpokenlanguage is not present in list. Must be added
			employeeSpokenlanguage.setEmployee(employeeToSave);
			
			employeeToSave.addEmployeeSpokenlanguage(employeeSpokenlanguage);
		}else {
			// TEST
			//
			System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: EmployeeSpokenlanguage was found ");
		
			
			// update it
			employeeSpokenlanguage.setEmployee(employeeToSave);  // ???????????????????????
			
			employeeSpokenlanguageToSaveInList.setYearsExperience(employeeSpokenlanguage.getYearsExperience());
			employeeSpokenlanguageToSaveInList.setProficiency(employeeSpokenlanguage.getProficiency());
			
		}

		// TEST
		//
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: ready to save");
		
		
		// Save Employee and his/her Spokenlanguages
		employeeRepository.save(employeeToSave);
		
		// TEST
		//
		System.out.println("cabelloej: EmployeeService.saveEmployeeSpokenlanguage: saved employeeToSave");		
		
		
		EmployeeSpokenlanguage savedEmployeeSpokenlanguage=  findByEmployeeIdAndSpokenlanguageId(employeeToSave.getId(), 
																								 employeeSpokenlanguage.getSpokenlanguage().getId());
				
		if (savedEmployeeSpokenlanguage==null) {

			throw new RuntimeException(  "EmployeeService.saveEmployeeSpokenlanguage: "
										+"Did not find savedEmployeeSpokenlanguage just saved... employee id - " + employeeToSave.getId()
										+"Spokenlanguage Id: " + employeeSpokenlanguage.getSpokenlanguage().getId() );
		}																						 
		
		return savedEmployeeSpokenlanguage;
		
	}

	
	
	@Override
	@Transactional
	public void deleteEmployeeSpokenlanguage(int employeeId, int spokenlanguageId) {

		// Get Employee
		Employee employeeToSave= findById(employeeId);
		if (employeeToSave==null) {

			throw new RuntimeException("EmployeeService.deleteEmployeeSpokenlanguage: Did not find employee id - " + employeeId);
		}

		// Determine if employeeSpokenlanguage is in Employee's List 
		EmployeeSpokenlanguage employeeSpokenlanguageToRemove= null;
		List<EmployeeSpokenlanguage> employeeSpokenlanguages= employeeToSave.getEmployeeSpokenlanguages();
		for(EmployeeSpokenlanguage tempEmployeeSpokenlanguage : employeeSpokenlanguages) { 
			 if(tempEmployeeSpokenlanguage.getSpokenlanguage().getId()== spokenlanguageId) { 
				 employeeSpokenlanguageToRemove= tempEmployeeSpokenlanguage;
			 }
		}
		
		if (employeeSpokenlanguageToRemove== null) {
			throw new RuntimeException(  "EmployeeService.deleteEmployeeSpokenlanguage: Did not find Spokenlanguage to delete... employee id - " + employeeId + " Spokenlanguage Id: " + spokenlanguageId );
		} else {
		
			// delete it
			employeeSpokenlanguageToRemove.setEmployee(null);
			employeeSpokenlanguages.remove(employeeSpokenlanguageToRemove);
			
			// Save Employee so we can DELETE his/her Spokenlanguages
			employeeRepository.save(employeeToSave);

		}
	

		
		return;
	}

}




















