package com.cabelloej.springcode.employee.service;

import java.util.List;
import com.cabelloej.springcode.employee.domain.Department;

public interface DepartmentService {

	public List<Department> findAll();
	
	public Department findById(int theId);
	
	public void save(Department theDepartment);
	
	public void deleteById(int theId);

}
