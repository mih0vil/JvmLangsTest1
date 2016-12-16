package hr.neos.jvmlang.java;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EmployeeServiceJLocal {

	List<Employee> getAllHavingDepartments();

	List<Employee> getAll();

}
