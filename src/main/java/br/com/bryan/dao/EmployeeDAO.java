package br.com.bryan.dao;

import java.util.List;

import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

public interface EmployeeDAO {
	Employee findById(Long id);

	Employee save(Employee exam);

	void update(Employee exam);

	void delete(Long id);

	List<Employee> findAll(SearchCriteria criteria);
}
