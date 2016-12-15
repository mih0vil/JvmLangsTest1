package hr.neos.jvmlang.employee

import javax.inject.Named

import org.omnifaces.cdi.ViewScoped
import java.io.Serializable
import hr.neos.jvmlang.kotlin.EmployeeServiceK
import javax.ejb.EJB
import hr.neos.jvmlang.kotlin.EmployeeServiceKLocal


@Named
@ViewScoped
open class MultiCultiSelectViewK : Serializable {

	@EJB
	private var employeeService : EmployeeServiceKLocal? = null  
	
	open val test = "Testing Kotlin"
	open val test2 = employeeService?.getAllHavingDepartments()
	
}