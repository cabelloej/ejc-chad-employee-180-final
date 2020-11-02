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

import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.domain.Spokenlanguage;
import com.cabelloej.springcode.employee.service.SpokenlanguageService;

@Controller
//@RequestMapping("/languages")
public class SpokenlanguageController {

	private SpokenlanguageService spokenlanguageService;
	
	public SpokenlanguageController(SpokenlanguageService theLanguageService) {
		spokenlanguageService= theLanguageService;
	}
	
	
	// Method to Validate all data coming from department forms
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor trimmerEditor= new StringTrimmerEditor(true);
		
		// For any String Class, this trim editor will apply
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
		
	}
	
	@GetMapping("/spokenlanguages/list")
	public String listSpokenanguages(Model theModel) {
		
		List<Spokenlanguage> theSpokenlanguages = spokenlanguageService.findAll();
		
		theModel.addAttribute("spokenlanguages", theSpokenlanguages);
		
		return "/spokenlanguages/list";
	}
	
	@GetMapping("/spokenlanguages/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Spokenlanguage theSpokenlanguages = new Spokenlanguage();
		theModel.addAttribute("spokenlanguage", theSpokenlanguages);
		
		return "/spokenlanguages/spokenlanguage-form";
	}

	@GetMapping("/spokenlanguages/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("spokenlanguageId") int theId, Model theModel) {
		
		Spokenlanguage theSpokenlanguages = spokenlanguageService.findById(theId);
		theModel.addAttribute("spokenlanguage", theSpokenlanguages);
		
		return "/spokenlanguages/spokenlanguage-form";	
	}
	
	
	@PostMapping("/spokenlanguages/save")
	public String saveSpokenlanguage(@Valid @ModelAttribute("spokenlanguage") Spokenlanguage thespokenlanguage, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return "/spokenanguages/spokenlanguage-form";	
			
		}else {

			spokenlanguageService.save(thespokenlanguage);

			return "redirect:/spokenlanguages/list";
		}
	}
	
	
	@GetMapping("/spokenlanguages/delete")
	public String delete(@RequestParam("spokenlanguageId") int theId) {
		
		spokenlanguageService.deleteById(theId);

		return "redirect:/spokenlanguages/list";
		
	}
	
}

