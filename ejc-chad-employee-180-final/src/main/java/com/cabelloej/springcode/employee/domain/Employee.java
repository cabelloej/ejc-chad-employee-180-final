package com.cabelloej.springcode.employee.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="employee")
@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
@ToString(exclude={"relatives", "employeeSpokenlanguages"})
@EqualsAndHashCode(exclude={"relatives", "employeeSpokenlanguages"})
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message="First Name required")
	@Size(min=1, max=45, message="First Name Min=1 Max=45 Character Long")
	@Column(name="first_name")
	private String firstName;
	
	@NotNull(message="Last Name required")
	@Size(min=1, max=45, message="Last Name Min=1 Max=45 Character Long")
	@Column(name="last_name")
	private String lastName;
	
	@Email(message="Valid email required")
	@Column(name="email")
	private String email;

	// Favorite programming languages:
	private Boolean java;
	private Boolean python;
	private Boolean dart;
	private Boolean kotlin;

	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="department_id")
	private Department department;

	
	@OneToMany(	mappedBy= "employee", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private List<Relative> relatives;

	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	private List<EmployeeSpokenlanguage> employeeSpokenlanguages;	
	   
	   
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(	name = "employee_sport", 
				joinColumns = @JoinColumn(name = "employee_id"), 
				inverseJoinColumns = @JoinColumn(name = "sport_id"))
	private List<Sport> likedSports;

	
 


    
    

	// Convinience Method to ADD Retaives to the Employee to keep bidirectional relation with Relatives Class
	public void addRelative(Relative tempRelative) {
		
		if (relatives== null) {
			relatives= new ArrayList<>();
		}
		
		relatives.add(tempRelative);
		tempRelative.setEmployee(this);
	}
	
	
	// Convinience Method to ADD Sports to the Employee to keep bidirectional relation with "employee_sports" Class
	public void addSport(Sport tempSport) {
		
		if (likedSports== null) {
			likedSports= new ArrayList<>();
		}
		
		likedSports.add(tempSport);
	}
	

	
	// Convinience Method to ADD Spoken Languages to the Employee to keep bidirectional relation with EmployeeSpokenLanguage Class
	public void addEmployeeSpokenlanguage(EmployeeSpokenlanguage tempEmployeeSpokenlanguage) {
		if (employeeSpokenlanguages== null) {
			employeeSpokenlanguages= new ArrayList<>();
		}
		
		employeeSpokenlanguages.add(tempEmployeeSpokenlanguage);
		tempEmployeeSpokenlanguage.setEmployee(this);
	}
	
	
	
		
}











