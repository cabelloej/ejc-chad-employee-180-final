package com.cabelloej.springcode.employee.service;

import java.util.List;

import com.cabelloej.springcode.employee.domain.Relative;

public interface RelativeService {

	/////////////////////////////////////////////////////////////////////
	//        Methods for future Macro-Management of relatives         
    //
	public List<Relative> macroFindAll();
	
	public Relative macroFindById(int theId);
	
	public void macroSave(Relative theRelative);
	
	public void macroDeleteById(int theId);

	public List<Relative> macroSearch(String theName);
	
}
