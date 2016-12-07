package hr.neos.jvmlang.employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
//import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import groovy.transform.TypeChecked
import hr.neos.jvmlang.groovy.EmployeeServiceG
import hr.neos.jvmlang.groovy.EmployeeServiceGLocal
import hr.neos.jvmlang.java.Country;
import hr.neos.jvmlang.java.Employee;
import hr.neos.jvmlang.java.EmployeeServiceJLocal;

//@ManagedBean
//@javax.faces.bean.ViewScoped
@Named
@ViewScoped
@TypeChecked
public class MultiCultiSelectViewG implements Serializable {
	
	private static final long serialVersionUID = 1L

	@EJB
	private EmployeeServiceGLocal employeeService
	
	List<Employee> selectableEmployees
	List<Employee> chosenEmployees
	List<Employee> allEmployees
	Set<Country> countries
	Set<Country> allCountries
	String countriesString=""

	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			System.out.println(this.class.name + " init()");
			chosenEmployees = new ArrayList<>();
			List<Employee> allEmps = employeeService.getAllHavingDepartments();
			selectableEmployees = allEmps;
			allCountries = new HashSet<>();
			for (Employee e : allEmps) {
				allCountries.add(e.department.location.country);
			}			
			refreshCountries();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	public void destroy() {
		System.out.println(this.class.name + " destroy()");
	}

	/**
	 * Refresh list of countries after change in selection of chosenEmployees
	 */
	private void refreshCountries() {
		countries = allCountries - chosenEmployees.collect({ it.department.location.country }).unique() 
		countriesString = String.join(', ', countries*.countryName)
	}
	
	/**
	 * Select employee from the table. Adds to list of selected and refreshes list of remaining countries 
	 * @param e
	 */
	public void selectEmployee(Employee employee) {
		try {
			chosenEmployees << employee
			selectableEmployees.remove(employee)
			refreshCountries()
		} catch (Exception e2) {
			System.err.println(e2)
		}
	}
	
	/**
	 * Clears list of selected employees
	 */
	public void clearEmployees() {
		selectableEmployees.addAll(chosenEmployees)
		chosenEmployees = new ArrayList<>()
		refreshCountries()
	}
	
}
