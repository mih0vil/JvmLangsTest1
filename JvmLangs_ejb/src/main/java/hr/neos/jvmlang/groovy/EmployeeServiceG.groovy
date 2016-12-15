package hr.neos.jvmlang.groovy

import java.util.List
import javax.ejb.Local
import javax.ejb.LocalBean
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

import groovy.transform.TypeChecked
import hr.neos.jvmlang.java.Employee
import hr.neos.jvmlang.java.EmployeeServiceJLocal

@TypeChecked
@Stateless
public class EmployeeServiceG implements EmployeeServiceGLocal {

	@PersistenceContext
	EntityManager em
	
    @Override
    List<Employee> getAllHavingDepartments() {
    	em.createNamedQuery("Employee.getAllHavingDepartments", Employee.class).getResultList()
    }
	
	@Override
	public List<Employee> getAll() {
		em.createNamedQuery("Employee.findAll", Employee.class).getResultList()
	}


}
