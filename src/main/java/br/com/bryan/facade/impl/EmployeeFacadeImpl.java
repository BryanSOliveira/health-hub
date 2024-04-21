package br.com.bryan.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.EmployeeBean;
import br.com.bryan.ejb.ExamTakenBean;
import br.com.bryan.facade.EmployeeFacade;
import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class EmployeeFacadeImpl implements EmployeeFacade {
	
	@EJB
	private EmployeeBean employeeBean;
	
	@EJB
	private ExamTakenBean examTakenBean;

	@Override
	public Employee findById(Long id) {
		return employeeBean.findById(id);
	}

	@Override
	public Employee save(Employee employee) throws ValidationException {
		validateEmployee(employee);
		
		return employeeBean.save(employee);
	}

	@Override
	public void update(Employee employee) throws ValidationException {
		validateEmployee(employee);
		
		employeeBean.update(employee);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		examTakenBean.deleteExamsTakenByEmployeeId(id);
		employeeBean.delete(id);
	}

	@Override
	public List<Employee> findAll(SearchCriteria criteria) {
		return employeeBean.findAll(criteria);
	}
	
	private void validateEmployee(Employee employee) throws ValidationException {
		if (employee.getName() == null || employee.getName().trim().isEmpty()) {
			throw new ValidationException("Invalid name.");
	    } else if (employee.getName().length() < 3) {
	        throw new ValidationException("Name must be at least 3 characters long.");
	    }
	}


}
