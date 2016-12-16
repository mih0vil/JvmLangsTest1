package hr.neos.jvmlang.employee

import javax.inject.Named

import org.omnifaces.cdi.ViewScoped
import java.io.Serializable
import hr.neos.jvmlang.kotlin.EmployeeServiceK
import javax.ejb.EJB
import hr.neos.jvmlang.kotlin.EmployeeServiceKLocal
import hr.neos.jvmlang.groovy.EmployeeServiceGLocal
import hr.neos.jvmlang.java.EmployeeServiceJLocal
import javax.annotation.*
import hr.neos.jvmlang.java.Employee
import hr.neos.jvmlang.java.Country
import java.util.HashSet

@Named
@ViewScoped
open class MultiCultiSelectViewK : Serializable {

	@EJB
	private var employeeService : EmployeeServiceKLocal? = null
	
	open var selectableEmployees: MutableList<Employee> = mutableListOf()
	open var chosenEmployees: MutableList<Employee> = mutableListOf()
	open var allEmployeesWithCountries: List<String> = listOf()
	open var countries: Set<Country> = setOf()
	lateinit private var allCountries: Set<Country>
	open var countriesString=""

	@PostConstruct
	open fun init(): Unit {
		try {
			println(this.javaClass.toString() + " init()")
			
			val empsWithDepartments = employeeService!!.getAllHavingDepartments()
			selectableEmployees = empsWithDepartments			
			allCountries = empsWithDepartments.map { it.department.location.country }.toSet()
			refreshCountries()
			initAllEmployees()			
		} catch (e: Exception) {
			e.printStackTrace()
		}		
	} 

	@PreDestroy
	open fun destroy(): Unit {
		println(this.javaClass.toString() + " destroy()")
	} 

	
	/**
	 * Refresh list of countries after change in selection of chosenEmployees
	 */
	private fun refreshCountries(): Unit {
		countries = allCountries - chosenEmployees.map { it.department.location.country }.distinct()
		countriesString = countries.map { it.countryName }.joinToString(", ")
	}
	
	/**
	 * Select employee from the table. Adds to list of selected and refreshes list of remaining countries 
	 * @param e
	 */
	open fun selectEmployee(employee: Employee): Unit {
		try {
			chosenEmployees.add(employee)
			selectableEmployees.remove(employee)
			refreshCountries()
		} catch (e2: Exception) {
			System.err.println(e2)
		}
	}
	
	/**
	 * Clears list of selected employees
	 */
	open fun clearEmployees(): Unit {
		selectableEmployees.addAll(chosenEmployees)
		chosenEmployees = mutableListOf()
		refreshCountries()
	}
	
	/**
	 * Gets all employees with departments and transform it to list
	 */
	private fun initAllEmployees(): Unit {
		val all = employeeService!!.all
		allEmployeesWithCountries = all.map { 
			val country = it.department?.location?.country?.countryName
			val display = if (country != null) "${it.firstName} ${it.lastName} (${country})" 
				else "${it.firstName} ${it.lastName}"
			display.toString() 
		}
	}
	

}