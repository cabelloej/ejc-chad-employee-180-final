package com.cabelloej.springcode.employee.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cabelloej.springcode.employee.domain.Department;
import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.service.DepartmentService;

@Controller
//@RequestMapping("/departments")
public class DepartmentController {

	private DepartmentService departmentService;
	
	public DepartmentController(DepartmentService theDepartmentService) {
		departmentService= theDepartmentService;
	}
	
	
	// Method to Validate all data coming from department forms
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor trimmerEditor= new StringTrimmerEditor(true);
		
		// For any String Class, this trim editor will apply
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
		
	}
	
	
	// add mapping for "/list"
	@GetMapping("/departments/list")
	public String listDepartments(Model theModel) {
		
		// get departments from db
		List<Department> theDepartments = departmentService.findAll();
		
		// add to the spring model
		theModel.addAttribute("departments", theDepartments);
		
		return "/departments/list-departments";
	}
	
	@GetMapping("/departments/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Department theDepartment = new Department();
		theModel.addAttribute("department", theDepartment);
		
		List<Employee> employees= theDepartment.getEmployees();
		theModel.addAttribute("employees", employees);
		
		return "/departments/department-form";
	}

	@GetMapping("/departments/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("departmentId") int theId, Model theModel) {
		
		Department theDepartment = departmentService.findById(theId);
		theModel.addAttribute("department", theDepartment);
		
		List<Employee> employees= theDepartment.getEmployees();
		theModel.addAttribute("employees", employees);
		
		return "/departments/department-form";			
	}
	
	
	@PostMapping("/departments/save")
	public String saveDepartment(@Valid @ModelAttribute("department") Department theDepartment, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return "/departments/department-form";	
			
		}else {
		
			// save the Department
			departmentService.save(theDepartment);
		
			// use a redirect to prevent duplicate submissions
			return "redirect:/departments/list";
		}
	}
	
	
	@GetMapping("/departments/delete")
	public String delete(@RequestParam("departmentId") int theId) {
		
		// delete the Department
		departmentService.deleteById(theId);
		
		// redirect to /departments/list
		return "redirect:/departments/list";
		
	}
	
}


















