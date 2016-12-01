package hr.neos.jvmlang.java;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class EmployeeServiceJ
 */
@Stateless
@LocalBean
public class EmployeeServiceJ implements EmployeeServiceJLocal {

	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public EmployeeServiceJ() {
    }
    
    @Override
    public List<Employee> getAllHavingDepartments() {
    	return em.createNamedQuery("Employee.getAllHavingDepartments", Employee.class).getResultList();
    }

}
