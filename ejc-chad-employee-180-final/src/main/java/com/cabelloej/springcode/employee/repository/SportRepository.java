package com.cabelloej.springcode.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cabelloej.springcode.employee.domain.Sport;

public interface SportRepository extends JpaRepository<Sport, Integer> {

		
}
