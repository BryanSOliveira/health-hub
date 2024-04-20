package br.com.bryan.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import br.com.bryan.config.DataSource;
import br.com.bryan.dao.EmployeeDAO;
import br.com.bryan.model.Employee;
import br.com.bryan.model.criteria.SearchCriteria;

@Singleton
public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public Employee findById(Long id) {
		String sql = "SELECT cd_funcionario, nm_funcionario FROM funcionario WHERE cd_funcionario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToEmployee(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Employee save(Employee employee) {
		String sql = "INSERT INTO funcionario (nm_funcionario) VALUES (?)";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, employee.getName());
			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                long id = generatedKeys.getLong(1);
	                employee.setId(id);
	            } else {
	                throw new SQLException("Creating employee failed, no ID obtained.");
	            }
	        }
			
	        return employee; 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(Employee employee) {
		String sql = "UPDATE funcionario SET nm_funcionario = ? WHERE cd_funcionario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, employee.getName());
			statement.setLong(2, employee.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM funcionario WHERE cd_funcionario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Employee> findAll(SearchCriteria criteria) {
		List<Employee> employees = new ArrayList<>();
		
		int pageSize = 25;
		if(criteria != null && criteria.getPageSize() > 0) {
			pageSize = criteria.getPageSize();
		}
		
		int offset = 0;
		if(criteria != null && criteria.getCurrentPage() > 1) {
			offset = (criteria.getCurrentPage() - 1) * pageSize;
		}
		
		StringBuilder sql = new StringBuilder("SELECT cd_funcionario, nm_funcionario FROM funcionario");
		
		List<String> conditions = new ArrayList<>();
	    if (criteria != null) {
	        if (criteria.getSearchQuery() != null && !criteria.getSearchQuery().isEmpty()) {
	        	try {
	                Long.parseLong(criteria.getSearchQuery());
	                conditions.add("cd_funcionario = ?");
	            } catch (NumberFormatException e) {
	                conditions.add("UPPER(nm_funcionario) LIKE UPPER(?)");
	            }
	        }
	    }
	    
	    if (!conditions.isEmpty()) {
	        sql.append(" WHERE ");
	        sql.append(String.join(" AND ", conditions));
	    }
	    sql.append(" ORDER BY nm_funcionario LIMIT ? OFFSET ?");
		
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql.toString())) {
			int paramIndex = 1;
	        if (criteria != null && criteria.getSearchQuery() != null && !criteria.getSearchQuery().isEmpty()) {
	            try {
	            	Long id = Long.parseLong(criteria.getSearchQuery());
	                statement.setLong(paramIndex++, id);
	            } catch (NumberFormatException e) {
	            	statement.setString(paramIndex++, "%" + criteria.getSearchQuery() + "%");
	            }
	        }
	        
	        statement.setInt(paramIndex++, pageSize);
	        statement.setInt(paramIndex, offset);
	        
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Employee employee = mapResultSetToEmployee(resultSet);
	                employees.add(employee);
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employees;
	}

	private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
		Employee employee = new Employee();
		employee.setId(resultSet.getLong("cd_funcionario"));
		employee.setName(resultSet.getString("nm_funcionario"));
		return employee;
	}
}
