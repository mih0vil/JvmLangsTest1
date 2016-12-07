package hr.neos.jvmlang.groovy;

import java.util.List

import javax.ejb.Local

import hr.neos.jvmlang.java.Employee

@Local
public interface EmployeeServiceGLocal {

	public List<Employee> getAllHavingDepartments()
	
	public List<Employee> getAll()
}
