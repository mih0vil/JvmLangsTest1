package hr.neos.jvmlang.groovy

import java.util.List

import javax.ejb.LocalBean
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

import hr.neos.jvmlang.java.Employee
import hr.neos.jvmlang.java.EmployeeServiceJLocal

@Stateless
//@LocalBean
public class EmployeeServiceG  {
	//implements EmployeeServiceJLocal

	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public EmployeeServiceJ() {
    }
    
//    @Override
    public List<Employee> getAllHavingDepartments() {
    	return em.createNamedQuery("Employee.getAllHavingDepartments", Employee.class).getResultList()
    }

}
