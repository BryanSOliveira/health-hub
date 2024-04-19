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
import br.com.bryan.dao.ExamDAO;
import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

@Singleton
public class ExamDAOImpl implements ExamDAO {

	@Override
	public Exam findById(Long id) {
		String sql = "SELECT * FROM exame WHERE cd_exame = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToExam(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Exam save(Exam exam) {
		String sql = "INSERT INTO exame (nm_exame, ic_ativo, ds_detalhe_exame, ds_detalhe_exame1) VALUES (?, ?, ?, ?)";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, exam.getName());
			statement.setInt(2, exam.getActive() != null && exam.getActive() ? 1 : 0);
			statement.setString(3, exam.getDetail1());
			statement.setString(4, exam.getDetail2());
			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                long id = generatedKeys.getLong(1);
	                exam.setId(id);
	            } else {
	                throw new SQLException("Creating exam failed, no ID obtained.");
	            }
	        }
			
	        return exam; 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(Exam exam) {
		String sql = "UPDATE exame SET nm_exame = ?, ic_ativo = ?, ds_detalhe_exame = ?, ds_detalhe_exame1 = ? WHERE cd_exame = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, exam.getName());
			statement.setInt(2, exam.getActive() != null && exam.getActive() ? 1 : 0);
			statement.setString(3, exam.getDetail1());
			statement.setString(4, exam.getDetail2());
			statement.setLong(5, exam.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM exame WHERE cd_exame = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Exam> findAll(SearchCriteria criteria) {
		List<Exam> exams = new ArrayList<>();
		
		int pageSize = 25;
		if(criteria != null && criteria.getPageSize() > 0) {
			pageSize = criteria.getPageSize();
		}
		
		int offset = 0;
		if(criteria != null && criteria.getCurrentPage() > 1) {
			offset = (criteria.getCurrentPage() - 1) * pageSize;
		}
		
		StringBuilder sql = new StringBuilder("SELECT cd_exame, nm_exame, ic_ativo, "
				+ "CONCAT(SUBSTRING(ds_detalhe_exame, 1, 100), IF(CHAR_LENGTH(ds_detalhe_exame) > 100, '...', '')) AS ds_detalhe_exame, "
				+ "CONCAT(SUBSTRING(ds_detalhe_exame1, 1, 100), IF(CHAR_LENGTH(ds_detalhe_exame1) > 100, '...', '')) AS ds_detalhe_exame1 "
				+ "FROM exame");
		
		List<String> conditions = new ArrayList<>();
	    if (criteria != null) {
	    	if(criteria.getActiveFilter() != null) {
		        switch (criteria.getActiveFilter()) {
		            case "active":
		                conditions.add("ic_ativo = 1");
		                break;
		            case "inactive":
		                conditions.add("ic_ativo = 0");
		                break;
		            case "all":
		                break;
		        }
	    	}
	        
	        if (criteria.getSearchQuery() != null && !criteria.getSearchQuery().isEmpty()) {
	        	try {
	                Long.parseLong(criteria.getSearchQuery());
	                conditions.add("cd_exame = ?");
	            } catch (NumberFormatException e) {
	                conditions.add("UPPER(nm_exame) LIKE UPPER(?)");
	            }
	        }
	    }
	    
	    if (!conditions.isEmpty()) {
	        sql.append(" WHERE ");
	        sql.append(String.join(" AND ", conditions));
	    }
	    sql.append(" ORDER BY cd_exame DESC LIMIT ? OFFSET ?");
		
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

	        System.out.println(statement.toString());
	        
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Exam exam = mapResultSetToExam(resultSet);
	                exams.add(exam);
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exams;
	}

	@Override
	public Exam findByName(String name) {
		String sql = "SELECT * FROM exame WHERE nm_exame = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, name);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToExam(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Exam mapResultSetToExam(ResultSet resultSet) throws SQLException {
		Exam exam = new Exam();
		exam.setId(resultSet.getLong("cd_exame"));
		exam.setName(resultSet.getString("nm_exame"));
		exam.setActive(resultSet.getBoolean("ic_ativo"));
		exam.setDetail1(resultSet.getString("ds_detalhe_exame"));
		exam.setDetail2(resultSet.getString("ds_detalhe_exame1"));
		return exam;
	}
}
