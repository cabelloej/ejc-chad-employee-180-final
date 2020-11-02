package com.cabelloej.springcode.employee.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="sport")
@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
@ToString()
@EqualsAndHashCode()
public class Sport {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@NotNull(message="Name required")
	@Size(min=1, max=45, message="Name Min=1 Max=45 Character Long")
	@Column(name="name")
	private String name;	
	
	@ManyToMany(mappedBy = "likedSports")
	private List<Employee> likedByEmployees;	

}
