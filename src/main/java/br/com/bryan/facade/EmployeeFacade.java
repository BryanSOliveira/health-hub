package br.com.bryan.facade;

import java.util.List;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

@Local
public interface EmployeeFacade {
	Employee findById(Long id);
	
	Employee save(Employee employee) throws ValidationException;
	
    void update(Employee employee) throws ValidationException;
    
    void delete(Long id);
    
    List<Employee> findAll(SearchCriteria criteria);
}
