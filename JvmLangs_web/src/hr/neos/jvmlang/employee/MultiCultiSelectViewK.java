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

import hr.neos.jvmlang.java.Country;
import hr.neos.jvmlang.java.Employee;
import hr.neos.jvmlang.java.EmployeeServiceJLocal;
import hr.neos.jvmlang.kotlin.EmployeeServiceKLocal;

//@ManagedBean
//@javax.faces.bean.ViewScoped
@Named
@ViewScoped
public class MultiCultiSelectViewK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private EmployeeServiceJLocal employeeService;
	
	@EJB
	private EmployeeServiceKLocal employeeServiceK;
	
	private List<Employee> selectableEmployees;
	private List<Employee> chosenEmployees;
	private Set<Country> countries;
	private Set<Country> allCountries;
	String countriesString="";
	private List<String> allEmployeesWithCountries;

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			System.out.println(this.getClass() + " init()");
			chosenEmployees = new ArrayList<>();
			List<Employee> allEmps = employeeService.getAllHavingDepartments();
			selectableEmployees = allEmps;
			allCountries = new HashSet<>();
			for (Employee e : allEmps) {
				allCountries.add(e.getDepartment().getLocation().getCountry());
			}			
			refreshCountries();
			initAllEmployees();
			System.out.println(employeeServiceK.getAllHavingDepartments());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	public void destroy() {
		System.out.println(this.getClass() + " destroy()");
	}

	public List<Employee> getSelectableEmployees() {
		return selectableEmployees;
	}

	/**
	 * Refresh list of countries after change in selection of chosenEmployees
	 */
	private void refreshCountries() {
		countries = new HashSet<>(allCountries);
		for (Employee e : chosenEmployees) {
			countries.remove(e.getDepartment().getLocation().getCountry());
		}
		List<String> countryNames = new ArrayList<>(countries.size());
		for (Country c : countries) {
			countryNames.add(c.getCountryName());
		}
		countriesString = String.join(", ", countryNames);
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

	public List<Employee> getChosenEmployees() {
		return chosenEmployees;
	}
	
	public void setChosenEmployees(List<Employee> employees) {
		this.chosenEmployees = employees;
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
			chosenEmployees.add(employee);
			selectableEmployees.remove(employee);
			refreshCountries();
		} catch (Exception e2) {
			System.err.println(e2);
		}
	}
	
	/**
	 * Clears list of selected chosenEmployees
	 */
	public void clearEmployees() {
		selectableEmployees.addAll(chosenEmployees);
		chosenEmployees = new ArrayList<>();
		refreshCountries();
	}

	public List<String> getAllEmployeesWithCountries() {
		return allEmployeesWithCountries;
	}

	public void setAllEmployeesWithCountries(List<String> allEmployeesWithCountries) {
		this.allEmployeesWithCountries = allEmployeesWithCountries;
	}
	
	
	/**
	 * Gets all employees with departments and transform it to list
	 */
	private void initAllEmployees() {
		List<Employee> all = employeeService.getAll();
		allEmployeesWithCountries = new ArrayList<>(all.size());
		for (Employee e : all) {
			String country = e.getDepartment() != null && e.getDepartment().getLocation() != null 
					&& e.getDepartment().getLocation().getCountry() != null 
					? e.getDepartment().getLocation().getCountry().getCountryName() 
					: null;
			allEmployeesWithCountries.add( country != null && country.length() > 0
					? String.format("%s %s (%s)", e.getFirstName(), e.getLastName(), country)
					: String.format("%s %s", e.getFirstName(), e.getLastName()));
		}
	}
	
}
