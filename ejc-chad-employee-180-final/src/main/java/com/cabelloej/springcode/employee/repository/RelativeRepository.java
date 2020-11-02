//
//
//	NO ES NECESARIO YA QUE LAS OPERACIONES LAS REALIZA EL REPOSITORIO PRINCIPAL (EMPLOYEE)
//
//  POSIBLE USO EN MANEJO MACRO DE RELATIVES
//
//
//
//
package com.cabelloej.springcode.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabelloej.springcode.employee.domain.Relative;

public interface RelativeRepository extends JpaRepository<Relative, Integer> {

	// add a method to sort by last name
	public List<Relative> findAllByOrderByLastNameAsc();
	
	// search by name
	public List<Relative> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);

}
