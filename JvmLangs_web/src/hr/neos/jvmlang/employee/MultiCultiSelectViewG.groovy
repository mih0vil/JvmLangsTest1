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
import hr.neos.jvmlang.java.Country;
import hr.neos.jvmlang.java.Employee;
import hr.neos.jvmlang.java.EmployeeServiceJLocal;

//@ManagedBean
//@javax.faces.bean.ViewScoped
@Named
@ViewScoped
@TypeChecked
public class MultiCultiSelectViewG implements Serializable, GroovyObject {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private EmployeeServiceJLocal employeeServiceJ;
	
	private List<Employee> selectableEmployees;
	private List<Employee> employees;
	private Set<Country> countries;
	private Set<Country> allCountries;
	String countriesString="";

	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			System.out.println(this.class.name + " init()");
			employees = new ArrayList<>();
			List<Employee> allEmps = employeeServiceJ.getAllHavingDepartments();
			selectableEmployees = allEmps;
			allCountries = new HashSet<>();
			for (Employee e : allEmps) {
				allCountries.add(e.getDepartment().getLocation().getCountry());
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

	public List<Employee> getSelectableEmployees() {
		return selectableEmployees;
	}

	/**
	 * Refresh list of countries after change in selection of employees
	 */
	private void refreshCountries() {
		countries = new HashSet<>(allCountries);
		for (Employee e : employees) {
			countries.remove(e.getDepartment().getLocation().getCountry());
		}
		List<String> countryNames = new ArrayList<>(countries.size());
		for (Country c : countries) {
			countryNames.add(c.getCountryName());
		}
		countriesString = String.join(", ", countryNames);
		System.out.println("countriesString: " + countriesString);
	}
	
	public void setSelectableEmployees(List<Employee> selectableEmployees) {
		this.selectableEmployees = selectableEmployees;
	}

	public Set<Country> getCountries() {
		return countries;
	}

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public String getCountriesString() {
		return countriesString;
	}

	public void setCountriesString(String countriesString) {
		this.countriesString = countriesString;
	}
	
	/**
	 * Select employee from the table. Adds to list of selected and refreshes list of remaining countries 
	 * @param e
	 */
	public void selectEmployee(Employee employee) {
		try {
			employees.add(employee);
			selectableEmployees.remove(employee);
			refreshCountries();
		} catch (Exception e2) {
			System.err.println(e2);
		}
	}
	
	/**
	 * Clears list of selected employees
	 */
	public void clearEmployees() {
		selectableEmployees.addAll(employees);
		employees = new ArrayList<>();
		refreshCountries();
	}
	
}
