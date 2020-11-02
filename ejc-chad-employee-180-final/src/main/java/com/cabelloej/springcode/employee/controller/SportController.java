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

import com.cabelloej.springcode.employee.domain.Sport;
import com.cabelloej.springcode.employee.service.SportService;

@Controller
//@RequestMapping("/sports")
public class SportController {

	private SportService sportService;
	
	public SportController(SportService theSportService) {
		sportService= theSportService;
	}
	
	
	// Method to Validate all data coming from Sport forms
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor trimmerEditor= new StringTrimmerEditor(true);
		
		// For any String Class, this trim editor will apply
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
		
	}
	
	@GetMapping("/sports/list")
	public String listSports(Model theModel) {
		
		List<Sport> theSports = sportService.findAll();
		
		theModel.addAttribute("sports", theSports);
		
		return "/sports/list-sports";
	}
	
	@GetMapping("/sports/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Sport theSport = new Sport();
		theModel.addAttribute("sport", theSport);
		
		return "/sports/sport-form";
	}

	@GetMapping("/sports/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("sportId") int theId, Model theModel) {
		
		Sport theSport = sportService.findById(theId);
		theModel.addAttribute("sport", theSport);
		
		return "/sports/sport-form";			
	}
	
	
	@PostMapping("/sports/save")
	public String saveSporte(@Valid @ModelAttribute("sport") Sport theSport, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return "/sports/sport-form";	
			
		}else {

			sportService.save(theSport);

			return "redirect:/sports/list";
		}
	}
	
	
	@GetMapping("/sports/delete")
	public String delete(@RequestParam("sportId") int theId) {
		
		sportService.deleteById(theId);

		return "redirect:/sports/list";
		
	}
	
}

