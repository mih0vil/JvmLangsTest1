package hr.neos.jvmlang.kotlin

import javax.ejb.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import hr.neos.jvmlang.java.Employee
import hr.neos.jvmlang.java.EmployeeServiceJLocal

/**
 * Session Bean implementation class EmployeeServiceJ
 */
@Stateless
class EmployeeServiceK : EmployeeServiceKLocal {
	
	@PersistenceContext
	private var em: EntityManager? = null
	
	override fun getAllHavingDepartments(): MutableList<Employee> {
		return em!!.createNamedQuery("Employee.getAllHavingDepartments", Employee::class.java ).getResultList()
	}
	
	override val all: List<Employee>
		get() {
			return em!!.createNamedQuery("Employee.findAll", Employee::class.java).getResultList()
		}
}



@Local
interface EmployeeServiceKLocal {
	
	fun getAllHavingDepartments(): MutableList<Employee>
	
	val all: List<Employee>
	
}
