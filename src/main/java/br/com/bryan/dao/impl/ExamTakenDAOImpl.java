package br.com.bryan.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import br.com.bryan.config.DataSource;
import br.com.bryan.dao.ExamTakenDAO;
import br.com.bryan.model.Employee;
import br.com.bryan.model.Exam;
import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

@Singleton
public class ExamTakenDAOImpl implements ExamTakenDAO {

	@Override
	public ExamTaken findById(Long id) {
		String sql = "SELECT cd_exame_realizado, cd_funcionario, cd_exame, dt_realizacao " +
                "FROM exame_realizado " +
                "WHERE cd_exame_realizado = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToExamTaken(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ExamTaken save(ExamTaken examTaken) {
		String sql = "INSERT INTO exame_realizado (cd_funcionario, cd_exame, dt_realizacao) VALUES (?, ?, ?)";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setLong(1, examTaken.getEmployee().getId());
			statement.setLong(2, examTaken.getExam().getId());
			statement.setDate(3, Date.valueOf(examTaken.getDate()));
			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                long id = generatedKeys.getLong(1);
	                examTaken.setId(id);
	            } else {
	                throw new SQLException("Creating exam taken failed, no ID obtained.");
	            }
	        }
			
	        return examTaken; 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(ExamTaken examTaken) {
		String sql = "UPDATE exame_realizado SET cd_funcionario = ?, cd_exame = ?, dt_realizacao = ? WHERE cd_exame_realizado = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, examTaken.getEmployee().getId());
			statement.setLong(2, examTaken.getExam().getId());
			statement.setDate(3, Date.valueOf(examTaken.getDate()));
			statement.setLong(4, examTaken.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM exame_realizado WHERE cd_exame_realizado = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ExamTaken> findAll(SearchCriteria criteria) {
		List<ExamTaken> examsTaken = new ArrayList<>();
		
		int pageSize = 25;
		if(criteria != null && criteria.getPageSize() > 0) {
			pageSize = criteria.getPageSize();
		}
		
		int offset = 0;
		if(criteria != null && criteria.getCurrentPage() > 1) {
			offset = (criteria.getCurrentPage() - 1) * pageSize;
		}
		
		StringBuilder sql = new StringBuilder("SELECT er.cd_exame_realizado, er.cd_funcionario, er.cd_exame, er.dt_realizacao, e.nm_exame, f.nm_funcionario "
				+ "FROM exame_realizado er "
				+ "JOIN exame e ON er.cd_exame = e.cd_exame "
				+ "JOIN funcionario f ON er.cd_funcionario = f.cd_funcionario");
		
		List<String> conditions = new ArrayList<>();
	    if (criteria != null) {
	        if (criteria.getSearchQuery() != null && !criteria.getSearchQuery().isEmpty()) {
	        	try {
	                Long.parseLong(criteria.getSearchQuery());
	                conditions.add("(er.cd_exame = ? OR er.cd_funcionario = ?)");
	            } catch (NumberFormatException e) {
	            	try {
	                    Date.valueOf(criteria.getSearchQuery());
	                    conditions.add("er.dt_realizacao = ?");
	                } catch (IllegalArgumentException iae) {
	                    conditions.add("(UPPER(e.nm_exame) LIKE UPPER(?) OR UPPER(f.nm_funcionario) LIKE UPPER(?))");
	                }
	            }
	        }
	    }
	    
	    if (!conditions.isEmpty()) {
	        sql.append(" WHERE ");
	        sql.append(String.join(" AND ", conditions));
	    }
	    sql.append(" ORDER BY er.dt_realizacao DESC, f.nm_funcionario LIMIT ? OFFSET ?");
		
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql.toString())) {
			int paramIndex = 1;
			if (criteria != null && criteria.getSearchQuery() != null && !criteria.getSearchQuery().isEmpty()) {
	        	try {
	                Long id = Long.parseLong(criteria.getSearchQuery());
	                statement.setLong(paramIndex++, id);
	                statement.setLong(paramIndex++, id);
	            } catch (NumberFormatException e) {
	            	try {
	                    Date date = Date.valueOf(criteria.getSearchQuery());
	                    statement.setDate(paramIndex++, date);
	                } catch (IllegalArgumentException iae) {
	                	statement.setString(paramIndex++, "%" + criteria.getSearchQuery() + "%");
	                	statement.setString(paramIndex++, "%" + criteria.getSearchQuery() + "%");
	                }
	            }
	        }
	        
	        statement.setInt(paramIndex++, pageSize);
	        statement.setInt(paramIndex, offset);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	            	ExamTaken examTaken = mapResultSetToExamTaken(resultSet);
	            	examsTaken.add(examTaken);
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return examsTaken;
	}

	private ExamTaken mapResultSetToExamTaken(ResultSet resultSet) throws SQLException {
	    ExamTaken examTaken = new ExamTaken();
	    examTaken.setId(resultSet.getLong("cd_exame_realizado"));

	    Employee employee = new Employee();
	    employee.setId(resultSet.getLong("cd_funcionario"));
	    if (hasColumn(resultSet, "nm_funcionario") && resultSet.getString("nm_funcionario") != null) {
	        employee.setName(resultSet.getString("nm_funcionario"));
	    }
	    examTaken.setEmployee(employee);

	    Exam exam = new Exam();
	    exam.setId(resultSet.getLong("cd_exame"));
	    if (hasColumn(resultSet, "nm_exame") && resultSet.getString("nm_exame") != null) {
	        exam.setName(resultSet.getString("nm_exame"));
	    }
	    examTaken.setExam(exam);

	    examTaken.setDate(resultSet.getDate("dt_realizacao").toLocalDate());
	    return examTaken;
	}

	private boolean hasColumn(ResultSet rs, String columnName) {
	    try {
	        rs.findColumn(columnName);
	        return true;
	    } catch (SQLException e) {
	        return false;
	    }
	}

	@Override
	public void deleteExamsTakenByEmployeeId(Long employeeId) {
		String sql = "DELETE FROM exame_realizado WHERE cd_funcionario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, employeeId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isExamTaken(Long examId) {
		String sql = "SELECT COUNT(*) FROM exame_realizado WHERE cd_exame = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setLong(1, examId);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return false;
	}

	@Override
	public boolean isDuplicateExamTaken(ExamTaken examTaken) {
		String sql = "SELECT COUNT(*) FROM exame_realizado WHERE cd_exame = ? AND cd_funcionario = ? AND dt_realizacao = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, examTaken.getExam().getId());
            statement.setLong(2, examTaken.getEmployee().getId());
            statement.setDate(3, Date.valueOf(examTaken.getDate()));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return false;
	}

	@Override
	public List<ExamTaken> findByDateRange(LocalDate startDate, LocalDate endDate) {
		List<ExamTaken> examsTaken = new ArrayList<>();
        String sql = "SELECT er.cd_exame_realizado, er.cd_funcionario, er.cd_exame, er.dt_realizacao, e.nm_exame, f.nm_funcionario "
				+ "FROM exame_realizado er "
				+ "JOIN exame e ON er.cd_exame = e.cd_exame "
				+ "JOIN funcionario f ON er.cd_funcionario = f.cd_funcionario "
				+ "WHERE er.dt_realizacao BETWEEN ? AND ? ORDER BY er.dt_realizacao";
        try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setDate(1, Date.valueOf(startDate));
        	statement.setDate(2, Date.valueOf(endDate));
            try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	            	ExamTaken examTaken = mapResultSetToExamTaken(resultSet);
	            	examsTaken.add(examTaken);
	            }
	        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examsTaken;
	}
	
}
