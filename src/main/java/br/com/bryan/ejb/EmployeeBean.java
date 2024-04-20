package br.com.bryan.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.bryan.dao.impl.EmployeeDAOImpl;
import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class EmployeeBean {

	@Inject
	private EmployeeDAOImpl employeeDAOImpl;
	
	public Employee findById(Long id) {
		return employeeDAOImpl.findById(id);
	}
	
	public Employee save(Employee employee) {
		return employeeDAOImpl.save(employee);
	}
	
	public void update(Employee employee) {
		employeeDAOImpl.update(employee);
	}
	
	public void delete(Long id) {
		employeeDAOImpl.delete(id);
	}
	
	public List<Employee> findAll(SearchCriteria criteria) {
		return employeeDAOImpl.findAll(criteria);
	}
}
