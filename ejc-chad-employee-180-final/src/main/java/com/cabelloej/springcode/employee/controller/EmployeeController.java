package com.cabelloej.springcode.employee.controller;

import java.util.ArrayList;
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

import com.cabelloej.springcode.employee.domain.Department;
import com.cabelloej.springcode.employee.domain.Employee;
import com.cabelloej.springcode.employee.domain.EmployeeSpokenlanguage;
import com.cabelloej.springcode.employee.domain.Relative;
import com.cabelloej.springcode.employee.domain.Spokenlanguage;
import com.cabelloej.springcode.employee.domain.Sport;
import com.cabelloej.springcode.employee.service.DepartmentService;
import com.cabelloej.springcode.employee.service.EmployeeService;
import com.cabelloej.springcode.employee.service.SpokenlanguageService;
import com.cabelloej.springcode.employee.service.SportService;

@Controller
//@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;
	private DepartmentService departmentService;
	private SportService sportService;
	private SpokenlanguageService spokenlanguageService;
	
	public Employee empoyeeInController= null;
	public Spokenlanguage spokenlanguageInController= null;
	
	public EmployeeController(	EmployeeService theEmployeeService, 
								DepartmentService theDepartmentService, 
								SportService theSportService,
								SpokenlanguageService theSpokenlanguageService) {
		employeeService= theEmployeeService;
		departmentService= theDepartmentService;
		sportService= theSportService;
		spokenlanguageService= theSpokenlanguageService;
	}
	
	
	// Method to Validate all data coming from employee forms
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor trimmerEditor= new StringTrimmerEditor(true);
		
		// For any String Class, this trim editor witll apply
		dataBinder.registerCustomEditor(String.class, trimmerEditor);
		
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods for controlling employees
	
	// add mapping for "/list"
	@GetMapping("/employees/list")
	public String listEmployees(Model theModel) {

		List<Employee> theEmployees = employeeService.findAll();

		theModel.addAttribute("employees", theEmployees);
		
		return "/employees/list-employees";
	}

	@GetMapping("/employees/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		Employee theEmployee = new Employee();
		theModel.addAttribute("employee", theEmployee);
		
		List<Department> departments= departmentService.findAll();
		theModel.addAttribute("departments", departments);
		
		List<Sport> sports= sportService.findAll();
		theModel.addAttribute("sports", sports);
		
//		List<EmployeeSpokenlanguage> employeeSpokenlanguages= theEmployee.getEmployeeSpokenlanguages();
//		theModel.addAttribute("employeeSpokenlanguages", employeeSpokenlanguages);
		
		return "/employees/employee-form";
	}

	@GetMapping("/employees/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		
		Employee theEmployee = employeeService.findById(theId);
		theModel.addAttribute("employee", theEmployee);
		
		List<Department> departments= departmentService.findAll();
		theModel.addAttribute("departments", departments);
		
		List<Sport> sports= sportService.findAll();
		theModel.addAttribute("sports", sports);
		
//		// DEBERIA FILTRAR LUEGO PARA NO MOSTRAR LOS SPOKENLANGUAGES QUE YA TIENE EL EMPLEADO
//		List<EmployeeSpokenlanguage> employeeSpokenlanguages= theEmployee.getEmployeeSpokenlanguages();
//		theModel.addAttribute("employeeSpokenlanguages", employeeSpokenlanguages);
		
		return "/employees/employee-form";			
	}
	
	@PostMapping("/employees/save")
	public String saveEmployee(	@Valid @ModelAttribute("employee") Employee theEmployee, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return "/employees/employee-form";	
			
		}else {

			employeeService.save(theEmployee);

			return "redirect:/employees/list";
		}
	}
	
	@GetMapping("/employees/delete")
	public String delete(@RequestParam("employeeId") int theId) {

		employeeService.deleteById(theId);

		return "redirect:/employees/list";
	}
	
	@GetMapping("/employees/search")
	public String delete(@RequestParam("employeeName") String theName,
						 Model theModel) {

		List<Employee> theEmployees = employeeService.searchBy(theName);

		theModel.addAttribute("employees", theEmployees);

		return "/employees/list-employees";
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods for controlling employees/relatives
    
    @GetMapping("employees/{employeeId}/relatives/add")
    public String newEmployeeRelative(@PathVariable String employeeId, Model model){
 	
        //make sure we have a good employeeId value
		Employee employee= employeeService.findById(Integer.parseInt(employeeId));
		if (employee== null) {

			throw new RuntimeException("Did not find employee id - " + employeeId);
		}
		
        //need to return back parent id for hidden form property
        Relative relative= new Relative();
        relative.setEmployee(employee);
      
        model.addAttribute("relative", relative);
        
        return "employees/relatives/relative-form";
    }

    @GetMapping("employees/{employeeId}/relatives/{id}/update")
    public String updateEmployeeRelative(@PathVariable String employeeId, @PathVariable String id, Model model){
    	
    	Relative relative= employeeService.findByEmployeeIdAndRelativeId(Integer.parseInt(employeeId), Integer.parseInt(id));
    	
        model.addAttribute( "relative",  relative);
        
        return "employees/relatives/relative-form";
    }
       
    @PostMapping("employees/relatives/save")
    public String saveOrUpdateEmployeeRelative(@Valid  @ModelAttribute ("relative") Relative relative, BindingResult bindingResult){
         	
     	if(bindingResult.hasErrors()) {
     		
    		return "employees/relatives/relative-form";
    		
    	}else {
    		
    		Relative savedRelative= employeeService.saveEmployeeRelative(relative);
        
    		return "redirect:/employees/list";
    	}
    }
    
    @GetMapping("employees/{employeeId}/relatives/{id}/delete")
    public String deleteEmployeeRelative(@PathVariable String employeeId, @PathVariable String id){

    	employeeService.deleteEmployeeRelative(Integer.parseInt(employeeId), Integer.parseInt(id));

        return "redirect:/employees/list";
    }
    
    


    ///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods for controlling employees/sports
 
	@GetMapping("/employees/{employeeId}/sports/select")
	public String selectSport(@PathVariable String employeeId, Model theModel) {
		
		Employee theEmployee= employeeService.findById(Integer.parseInt(employeeId));
		
		List<Sport> theSports = sportService.findAll();
		
		theModel.addAttribute("employee", theEmployee);
		
		theModel.addAttribute("sports", theSports);
		
		return "/sports/select-sport";
	}
    
    
    @GetMapping("employees/{employeeId}/sports/{id}/add")
    public String addEmployeeSport(@PathVariable String employeeId, @PathVariable String id, Model model){
    	
    	Sport likedSport= sportService.findById(Integer.parseInt(id));
    	
    	employeeService.saveLikedSport(Integer.parseInt(employeeId), likedSport);
           
        return "redirect:/employees/list";
    }
	
    
    @GetMapping("employees/{employeeId}/sports/{id}/delete")
    public String deleteEmployeeSport(@PathVariable String employeeId, @PathVariable String id, Model model){
    	
    	Sport likedSport= sportService.findById(Integer.parseInt(id));
    	
    	employeeService.deleteLikedSport(Integer.parseInt(employeeId), likedSport);
           
        return "redirect:/employees/list";
    }
	
 
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Methods for controlling employees/Spokenlanguages
    
    @GetMapping("employees/{employeeId}/spokenlanguages/add")
    public String newEmployeeSpokenlanguage(@PathVariable String employeeId, Model model){
 	
        //make sure we have a good employeeId value
		Employee employee= employeeService.findById(Integer.parseInt(employeeId));
		if (employee== null) {

			throw new RuntimeException("Did not find employee id - " + employeeId);
		}
		
        //need to return back parent id for hidden form property
        EmployeeSpokenlanguage employeeSpokenlanguage= new EmployeeSpokenlanguage();
        employeeSpokenlanguage.setEmployee(employee);
        employeeSpokenlanguage.setSpokenlanguage(new Spokenlanguage());
        model.addAttribute("employeeSpokenlanguage", employeeSpokenlanguage);
        
        // Get all spokenlanguages in db and exclude the spokenlanguages already in employee file
		List<Spokenlanguage> spokenlanguages= spokenlanguageService.findAll();
		List<Spokenlanguage> spokenlanguagesAlready= new ArrayList<>();
		List<EmployeeSpokenlanguage> employeeSpokenlanguages= employee.getEmployeeSpokenlanguages();
		for (EmployeeSpokenlanguage tempESL : employeeSpokenlanguages) {
			spokenlanguagesAlready.add(tempESL.getSpokenlanguage());
		}
		spokenlanguages.removeAll(spokenlanguagesAlready);
		model.addAttribute("spokenlanguages", spokenlanguages);
//         // Get all spokenlanguages in db 
//		List<Spokenlanguage> spokenlanguages= spokenlanguageService.findAll();
//		// Get all employeespokenlanguages from employee
//		List<EmployeeSpokenlanguage> employeeSpokenlanguages= employee.getEmployeeSpokenlanguages();
//		// Exclude the spokenlanguages already in employee file
//		List<Spokenlanguage> spokenlanguagesAlready= new ArrayList<>();
//		for (EmployeeSpokenlanguage tempESL : employeeSpokenlanguages) {
//			spokenlanguagesAlready.add(tempESL.getSpokenlanguage());
//		}
//		spokenlanguages.removeAll(spokenlanguagesAlready);
        
        
		// TEST
		//
		System.out.println("cabelloej: EmployeeController.newEmployeeSpokenlanguage: ending .... Employee Id  : "+employeeSpokenlanguage.getEmployee().getId());
		System.out.println("cabelloej: EmployeeController.newEmployeeSpokenlanguage: ending .... Employee name: "+employeeSpokenlanguage.getEmployee().getFirstName());
		System.out.println("cabelloej: EmployeeController.newEmployeeSpokenlanguage: ending .... Language name: "+employeeSpokenlanguage.getSpokenlanguage().getName());
		System.out.println("cabelloej: EmployeeController.newEmployeeSpokenlanguage: ending .... Years        : "+employeeSpokenlanguage.getYearsExperience());
		System.out.println("cabelloej: EmployeeController.newEmployeeSpokenlanguage: ending .... Proficiency  : "+employeeSpokenlanguage.getProficiency());
		System.out.println("cabelloej: EmployeeController.newEmployeeSpokenlanguage: ending .... going to employeespokenlanguage-form: ");
        
        
        return "employees/employeesspokenlanguages/employeespokenlanguage-form";
    }

    @GetMapping("employees/{employeeId}/spokenlanguages/{id}/update")
    public String updateEmployeeSpokenlanguage(@PathVariable String employeeId, @PathVariable String id, Model model){
    	
  
    	//make sure we have a good employeeId value
		Employee employee= employeeService.findById(Integer.parseInt(employeeId));
		if (employee== null) {

			throw new RuntimeException("Did not find employee id - " + employeeId);
		}
		
        //need to return back parent id for hidden form property
    	EmployeeSpokenlanguage employeeSpokenlanguage= employeeService.findByEmployeeIdAndSpokenlanguageId(Integer.parseInt(employeeId), Integer.parseInt(id));
    	if (employeeSpokenlanguage== null) {

			throw new RuntimeException("Did not find spokenlanguage id - " + id + "for employee id - " + employeeId);
		}
		employeeSpokenlanguage.getSpokenlanguage().setSpokenlanguageEmployees(null);
    	model.addAttribute( "employeeSpokenlanguage",  employeeSpokenlanguage);		

    	
    	
        // Get all spokenlanguages in db 
		List<Spokenlanguage> spokenlanguages= spokenlanguageService.findAll();
		// Get all employeespokenlanguages from employee
		List<EmployeeSpokenlanguage> employeeSpokenlanguages= employee.getEmployeeSpokenlanguages();
		// Exclude the spokenlanguages already in employee file
		List<Spokenlanguage> spokenlanguagesAlready= new ArrayList<>();
		for (EmployeeSpokenlanguage tempESL : employeeSpokenlanguages) {
			spokenlanguagesAlready.add(tempESL.getSpokenlanguage());
		}
		spokenlanguages.removeAll(spokenlanguagesAlready);

		
		
		// Add spokenlanguage to be edited to the list
		spokenlanguages.add(employeeSpokenlanguage.getSpokenlanguage());
		model.addAttribute("spokenlanguages", spokenlanguages);
  
        
		// TEST
		//
		System.out.println("cabelloej: EmployeeController.updateEmployeeSpokenlanguage: ending .... Employee Id  : "+employeeSpokenlanguage.getEmployee().getId());
		System.out.println("cabelloej: EmployeeController.updateEmployeeSpokenlanguage: ending .... Employee name: "+employeeSpokenlanguage.getEmployee().getFirstName());
		System.out.println("cabelloej: EmployeeController.updateEmployeeSpokenlanguage: ending .... Language Id  : "+employeeSpokenlanguage.getSpokenlanguage().getId());
		System.out.println("cabelloej: EmployeeController.updateEmployeeSpokenlanguage: ending .... Years        : "+employeeSpokenlanguage.getYearsExperience());
		System.out.println("cabelloej: EmployeeController.updateEmployeeSpokenlanguage: ending .... Proficiency  : "+employeeSpokenlanguage.getProficiency());
		System.out.println("cabelloej: EmployeeController.updateEmployeeSpokenlanguage: ending .... going to employeespokenlanguage-form: ");
        
        
        return "employees/employeesspokenlanguages/employeespokenlanguage-form";
      
        
    }
       
    @PostMapping("employees/spokenlanguages/save")
    public String saveOrUpdateEmployeeSpokenlanguage(@Valid  @ModelAttribute ("employeeSpokenlanguage") EmployeeSpokenlanguage employeeSpokenlanguage, BindingResult bindingResult){
         
		// TEST
		//
		System.out.println("cabelloej: EmployeeController.saveOrUpdateEmployeeSpokenlanguage: starting ....");
		System.out.println("cabelloej: EmployeeController.saveOrUpdateEmployeeSpokenlanguage: starting .... Employee Id  : "+employeeSpokenlanguage.getEmployee().getId());
		System.out.println("cabelloej: EmployeeController.saveOrUpdateEmployeeSpokenlanguage: starting .... Employee name: "+employeeSpokenlanguage.getEmployee().getFirstName());
		System.out.println("cabelloej: EmployeeController.saveOrUpdateEmployeeSpokenlanguage: starting .... Language Id  : "+employeeSpokenlanguage.getSpokenlanguage().getId());
		System.out.println("cabelloej: EmployeeController.saveOrUpdateEmployeeSpokenlanguage: starting .... Years        : "+employeeSpokenlanguage.getYearsExperience());
		System.out.println("cabelloej: EmployeeController.saveOrUpdateEmployeeSpokenlanguage: starting .... Proficiency  : "+employeeSpokenlanguage.getProficiency());
  	
     	if(bindingResult.hasErrors()) {
     		
            return "employees/employeesspokenlanguages/employeespokenlanguage-form";
    		
    	}else {
    		
    		employeeService.saveEmployeeSpokenlanguage(employeeSpokenlanguage);
        
    		return "redirect:/employees/list";
    	}
    }
    
    
    @GetMapping("employees/{employeeId}/spokenlanguages/{id}/delete")
    public String deleteEmployeeSpokenlanguage(@PathVariable String employeeId, @PathVariable String id){

    	employeeService.deleteEmployeeSpokenlanguage(Integer.parseInt(employeeId), Integer.parseInt(id));

        return "redirect:/employees/list";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
//	@GetMapping("/employees/{employeeId}/employeesspokenlanguages/select")
//	public String selectSpokenlanguage(@PathVariable String employeeId, Model theModel) {
//		
//		Employee theEmployee= employeeService.findById(Integer.parseInt(employeeId));
//		
//		theModel.addAttribute("employee", theEmployee);
//		
//		List<Spokenlanguage> theSpokenlanguages= spokenlanguageService.findAll(); // should be refined to exclude existing employee's spokenlanguages
//		
//		theModel.addAttribute("spokenlanguages", theSpokenlanguages);
//		
//		// TEST
//		//
//		System.out.println("cabelloej: EmployeeController.selectSpokenlanguage: Sending to language selection....");
//		
//		return "/employees/employeesspokenlanguages/select-spokenlanguage";
//	}
//    
// 
//	@GetMapping("/employees/{employeeId}/employeesspokenlanguages/{id}/showform")
//	public String showEmployeeSpokenlanguage(@PathVariable String employeeId, @PathVariable String id, Model theModel) {	
//
//        //make sure we have a good employeeId value
//		Employee employee= employeeService.findById(Integer.parseInt(employeeId));
//		if (employee== null) {
//
//			throw new RuntimeException("cabelloej EmployeeController.showSpokenlanguage: Did not find employee id - " + employeeId);
//		}
//		
//        //make sure we have a good id value
//		Spokenlanguage spokenlanguage= spokenlanguageService.findById(Integer.parseInt(id));
//		if (spokenlanguage== null) {
//
//			throw new RuntimeException("cabelloej EmployeeController.showSpokenlanguage: Did not find spokenlanguage id - " + id);
//		}
//		
//        //need to return back parent id for hidden form property
//		EmployeeSpokenlanguage employeeSpokenlanguageForForm= null;
//		
//		// employeeSpokenlanguageForForm= employeeService.findByEmployeeIdAndSpokenlanguageId(Integer.parseInt(employeeId), Integer.parseInt(id));
//		List<EmployeeSpokenlanguage> employeeSpokenLangList= employee.getEmployeeSpokenlanguages();
//		
//		if (!employeeSpokenLangList.isEmpty()) {
//			for(EmployeeSpokenlanguage tempESL : employeeSpokenLangList) { 
//				if(tempESL.getSpokenlanguage().getId()== spokenlanguage.getId()) { 
//					employeeSpokenlanguageForForm= tempESL;
//				}
//			}
//		}
//		if (employeeSpokenlanguageForForm== null) {
//			employeeSpokenlanguageForForm= new EmployeeSpokenlanguage();
//			employeeSpokenlanguageForForm.setEmployee(employee);
//			employeeSpokenlanguageForForm.setSpokenlanguage(spokenlanguage);
//		}
//
//		// TEST
//		//
//		System.out.println("cabelloej: EmployeeController.showEmployeeSpokenlanguage: ending .... Employee name: "+employeeSpokenlanguageForForm.getEmployee().getFirstName());
//		System.out.println("cabelloej: EmployeeController.showEmployeeSpokenlanguage: ending .... Language name: "+employeeSpokenlanguageForForm.getSpokenlanguage().getName());
//		System.out.println("cabelloej: EmployeeController.showEmployeeSpokenlanguage: ending .... Years        : "+employeeSpokenlanguageForForm.getYearsExperience());
//		System.out.println("cabelloej: EmployeeController.showEmployeeSpokenlanguage: ending .... Proficiency  : "+employeeSpokenlanguageForForm.getProficiency());
//		System.out.println("cabelloej: EmployeeController.showEmployeeSpokenlanguage: ending .... going to employeespokenlanguage-form: ");
//
////		theModel.addAttribute("employeeForForm", employee);
////		theModel.addAttribute("spokenlanguageForForm", spokenlanguage);
//		empoyeeInController= employeeSpokenlanguageForForm.getEmployee();
//		spokenlanguageInController= employeeSpokenlanguageForForm.getSpokenlanguage();
//		
//		
//		theModel.addAttribute("employeeSpokenlanguageForForm", employeeSpokenlanguageForForm);
//		
//		return "employees/employeesspokenlanguages/employeespokenlanguage-form";
//	}
//	
//
//	@PostMapping("employees/employeesspokenlanguages/save")
//    public String addEmployeeSpokenlanguage(	@Valid 
//    											@ModelAttribute("employeeSpokenlanguageForForm") EmployeeSpokenlanguage employeeSpokenlanguageToSave, 
//    											BindingResult bindingResult) {
////												@ModelAttribute("employeeForForm") Employee employeeToSave,
////												@ModelAttribute("spokenlanguageForForm") Spokenlanguage spokenlanguageToSave,
//
//		
//		// TEST
//		//
//		System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Starting ...");
//		System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Years        : "+employeeSpokenlanguageToSave.getYearsExperience());
//		System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Proficiency  : "+employeeSpokenlanguageToSave.getProficiency());
//		//System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Employee Id  : "+employeeSpokenlanguageToSave.getEmployee().getId());
//		//System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Language Id  : "+employeeSpokenlanguageToSave.getSpokenlanguage().getId());
//		//System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Employee Name: "+employeeSpokenlanguageToSave.getEmployee().getFirstName());
//		//System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Language Name: "+employeeSpokenlanguageToSave.getSpokenlanguage().getName());	
//		System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Employee Id  : "+empoyeeInController.getId());
//		System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Employee Name: "+empoyeeInController.getFirstName());
//		System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Language Id  : "+spokenlanguageInController.getId());
//		System.out.println("cabelloej: EmployeeController.addEmployeeSpokenlanguage: Language Name: "+spokenlanguageInController.getName());	
//		
//		if(bindingResult.hasErrors()) {
//			
//			System.out.println("cabelloej: EmployeeController / addEmployeeSpokenlanguage / if(bindingResult.hasErrors())= True ...... ");
//		
//			return "employees/employeesspokenlanguages/employeespokenlanguage-form";
//    		
//    	}else {
//
//			System.out.println("cabelloej: EmployeeController / addEmployeeSpokenlanguage / if(bindingResult.hasErrors())= False ...... ");
//    		
//    		employeeService.saveEmployeeSpokenlanguage(employeeSpokenlanguageToSave	);
//        
//    		return "redirect:/employees/list";
//    	}
//
//
// 	}
	
   
   
    
    
    
    
    
//    
//    @GetMapping("employees/{employeeId}/sports/{id}/update")
//    public String zzzaddEmployeeSport(@PathVariable String employeeId, @PathVariable String id, Model model){
//    	
//    	Sport likedSport= sportService.findById(Integer.parseInt(id));
//    	
//    	employeeService.saveLikedSport(Integer.parseInt(employeeId), likedSport);
//           
//        return "redirect:/employees/list";
//    }
//    
//    
//    @GetMapping("employees/{employeeId}/sports/{id}/delete")
//    public String xxdeleteEmployeeSport(@PathVariable String employeeId, @PathVariable String id, Model model){
//    	
//    	Sport likedSport= sportService.findById(Integer.parseInt(id));
//    	
//    	employeeService.deleteLikedSport(Integer.parseInt(employeeId), likedSport);
//           
//        return "redirect:/employees/list";
//    }
}


