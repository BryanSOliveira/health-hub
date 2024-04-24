package br.com.bryan.facade;

import java.util.List;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.exceptions.EntityNotFoundException;
import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

@Local
public interface EmployeeFacade {
	Employee findById(Long id) throws EntityNotFoundException;
	
	Employee save(Employee employee) throws ValidationException;
	
    void update(Employee employee) throws ValidationException, EntityNotFoundException;
    
    void delete(Long id) throws EntityNotFoundException;
    
    List<Employee> findAll(SearchCriteria criteria);
}
