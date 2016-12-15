package hr.neos.jvmlang.kotlin

import hr.neos.jvmlang.java.Employee
import javax.ejb.Local

@Local
interface EmployeeServiceKLocal {
	
	fun getAllHavingDepartments(): List<Employee>
	
	val all: List<Employee>?
	
}
