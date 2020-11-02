package com.cabelloej.springcode.employee.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="employee_spokenlanguage")
@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
@ToString()
@EqualsAndHashCode()
public class EmployeeSpokenlanguage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

    @ManyToOne(	cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @ManyToOne(	cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "spokenlanguage_id")
    private Spokenlanguage spokenlanguage;
    
	//@NotNull(message="Years required")
	//@Size(min=0, max=100, message="Years of Experience Min=0 Max=100")
	@Column(name="years_experience")
	private int yearsExperience;	
	
	//@NotNull(message="Proficiency required")
	//@Size(min=1, max=45, message="Proficiency Min=1  Max=45 Charactes Long")
	@Column(name="proficiency")
	private Proficiency proficiency;
		
}
