package br.com.bryan.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.EmployeeBean;
import br.com.bryan.facade.EmployeeFacade;
import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class EmployeeFacadeImpl implements EmployeeFacade {
	
	@EJB
	EmployeeBean employeeBean;

	@Override
	public Employee findById(Long id) {
		return employeeBean.findById(id);
	}

	@Override
	public Employee save(Employee employee) throws ValidationException {
		if (employee.getName() == null || employee.getName().trim().isEmpty()) {
			throw new ValidationException("Invalid name.");
	    } else if (employee.getName().length() < 3) {
	        throw new ValidationException("Name must be at least 3 characters long.");
	    }
		
		return employeeBean.save(employee);
	}

	@Override
	public void update(Employee employee) throws ValidationException {
		if (employee.getName() == null || employee.getName().trim().isEmpty()) {
			throw new ValidationException("Invalid name.");
	    } else if (employee.getName().length() < 3) {
	        throw new ValidationException("Name must be at least 3 characters long.");
	    }
		
		employeeBean.update(employee);
	}

	@Override
	public void delete(Long id) {
		employeeBean.delete(id);
	}

	@Override
	public List<Employee> findAll(SearchCriteria criteria) {
		return employeeBean.findAll(criteria);
	}

}
