package com.cabelloej.springcode.employee.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity()
@Table(name="relative")
@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
@ToString(exclude="employee")
@EqualsAndHashCode(exclude = {"employee"})
public class Relative {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(	cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	@NotNull(message="First Name required")
	@Size(min=1, max=45, message="First Name Min=1 Max=45 Character Long")
	@Column(name="first_name")
	private String firstName;
	
	@NotNull(message="Last Name required")
	@Size(min=1, max=45, message="Last Name Min=1 Max=45 Character Long")
	@Column(name="last_name")
	private String lastName;
	
	@NotNull(message="Relation required")
	@Size(min=1, max=25, message="Relation Min=1 Max=25 Character Long")
	@Column(name="relation")
	private String relation;
	
	@NotNull(message="Gender required")
	@Size(min=1, max=25, message="Gender Min=1 Max=25 Character Long")
	@Column(name="gender")
	private String gender;
	
	@NotNull(message="Birth date required")
	@PastOrPresent(message="Birth date must be past or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="birth_date")
	private Date birthDate;

	@Min(value=(long) .1, message="Min. 0.1 mt")
	@Max(value=(long) 2.5, message="Max. 2.5 mt")
	@Column(name="height")
	private Double height;
	
	@Min(value=(long) 2, message="Min. 2kg")
	@Max(value=(long) 250, message="Max. 250kg")	
	@Column(name="weight")
	private Double weight;
		
	@Min(value=(long) 10, message="Min. 10cm")
	@Max(value=(long) 200, message="Max. 200cm")	
	@Column(name="wrist_size")
	private Integer wristSize;
	
	@Size(max=25)
	@Column(name="favorite_color")
    private String favoriteColor;
    
 
	//@NotNull(message="Support Deadline required")
	@Future(message="Support Deadline must be future date")
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="support_deadline")
    private Date supportDeadline;
    
    
}

