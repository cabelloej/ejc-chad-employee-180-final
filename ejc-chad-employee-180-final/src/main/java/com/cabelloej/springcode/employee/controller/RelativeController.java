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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.domain.Relative;
import com.cabelloej.springcode.employee.service.EmployeeService;
import com.cabelloej.springcode.employee.service.RelativeService;

@Controller
//@RequestMapping("/relatives")
public class RelativeController {
	private EmployeeService employeeService;
	private RelativeService relativeService;
	
	public RelativeController(EmployeeService theEmployeeService, RelativeService theRelativeService) {
		employeeService= theEmployeeService;
		relativeService= theRelativeService;
	}
	
    
	//
	// Method to Process/Validate all data coming from employee forms
    // Right now it just trim all String types
    //
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor trimmerEditor= new StringTrimmerEditor(true);
		
		// For any String Class, this trim editor witll apply
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
		
	}

	
	// OJO: COPIADO DE RECIPES/INGREDIENTES... No creo necesitarlo
    @GetMapping("/employees/{employeeId}/relatives")
    public String listRelatives(@PathVariable String employeeId, Model model){
        // log.debug("Getting relatives list for employee id: " + employeeId);

        // watch for lazy load errors in Thymeleaf. (don't know how yet...)
        model.addAttribute( "employee", employeeService.findById(Integer.parseInt(employeeId)) );
        return "employees/relatives/list";
    }
	

	// OJO: COPIADO DE RECIPES/INGREDIENTES... No creo necesitarlo
    @GetMapping("/employees/{employeeId}/relatives/{id}/show")
    public String showEmployeeRelative(@PathVariable String employeeId, @PathVariable String id, Model model){
        model.addAttribute( "relative", employeeService.findByEmployeeIdAndRelativeId(Integer.parseInt(employeeId), Integer.parseInt(id)) );
        return "employees/relatives/show";
    }

	

   
  	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////// IMPLEMENTACION DE LOS METODOS PARA FUTURO MANEJO MACRO DE RELATIVES /////////////////////
	////////////  REVISAR SI ESTOY USANDO ALGUNO DE ESTOS METODOS ACCIDENTALMENTE   //////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/relatives/macroList")
	public String listRelatives(Model theModel) {
		
		List<Relative> theRelatives = relativeService.macroFindAll();
		theModel.addAttribute("relatives", theRelatives);
		
		return "/employees/relatives/list-relatives";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Relative theRelative = new Relative();
		theModel.addAttribute("relative", theRelative);
		
		return "/employees/relatives/relative-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("relativeId") int theId, Model theModel) {

		Relative theRelative = relativeService.macroFindById(theId);
		theModel.addAttribute("relative", theRelative);

		return "/employees/relative/relative-form";			
	}
	
	
	@PostMapping("/save")
	public String saveRelatives(@ModelAttribute("relative") Relative theRelative) {

		relativeService.macroSave(theRelative);

		return "redirect:/employees/relatives/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("relativeId") int theId) {

		relativeService.macroDeleteById(theId);

		return "redirect:/employees/relatives/list";
	}
	
	@GetMapping("/employees/relatives/search")
	public String search(@RequestParam("relativeName") String theName, Model theModel) {

		List<Relative> theRelatives = relativeService.macroSearch(theName);
		theModel.addAttribute("relatives", theRelatives);

		return "/employees/relatives/list-relatives";
	}
	
}


















