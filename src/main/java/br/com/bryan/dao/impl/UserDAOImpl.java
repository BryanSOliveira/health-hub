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
import br.com.bryan.dao.UserDAO;
import br.com.bryan.model.User;
import br.com.bryan.model.criteria.SearchCriteria;

@Singleton
public class UserDAOImpl implements UserDAO {

	@Override
	public User findById(Long id) {
		String sql = "SELECT cd_usuario, nm_login, qt_tempo_inatividade FROM usuario WHERE cd_usuario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToUser(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User findByUsername(String username) {
		String sql = "SELECT cd_usuario, nm_login, ds_senha, qt_tempo_inatividade FROM usuario WHERE nm_login = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, username);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToUser(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User save(User user) {
		String sql = "INSERT INTO usuario (nm_login, ds_senha, qt_tempo_inatividade) VALUES (?, ?, ?)";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getInactiveTime());
			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                long id = generatedKeys.getLong(1);
	                user.setId(id);
	            } else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
			
	        return user; 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE usuario SET nm_login = ?, qt_tempo_inatividade = ? WHERE cd_usuario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user.getUsername());
			statement.setInt(2, user.getInactiveTime());
			statement.setLong(3, user.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM usuario WHERE cd_usuario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> findAll(SearchCriteria criteria) {
		List<User> users = new ArrayList<>();
		
		int pageSize = 25;
		if(criteria != null && criteria.getPageSize() > 0) {
			pageSize = criteria.getPageSize();
		}
		
		int offset = 0;
		if(criteria != null && criteria.getCurrentPage() > 1) {
			offset = (criteria.getCurrentPage() - 1) * pageSize;
		}
		
		StringBuilder sql = new StringBuilder("SELECT cd_usuario, nm_login, qt_tempo_inatividade FROM usuario");
		
		List<String> conditions = new ArrayList<>();
	    if (criteria != null) {
	        if (criteria.getSearchQuery() != null && !criteria.getSearchQuery().isEmpty()) {
	        	try {
	                Long.parseLong(criteria.getSearchQuery());
	                conditions.add("cd_usuario = ?");
	            } catch (NumberFormatException e) {
	                conditions.add("UPPER(nm_login) LIKE UPPER(?)");
	            }
	        }
	    }
	    
	    if (!conditions.isEmpty()) {
	        sql.append(" WHERE ");
	        sql.append(String.join(" AND ", conditions));
	    }
	    sql.append(" ORDER BY nm_login LIMIT ? OFFSET ?");
		
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
	            	User user = mapResultSetToUser(resultSet);
	                users.add(user);
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("cd_usuario"));
		user.setUsername(resultSet.getString("nm_login"));
		
		if (hasColumn(resultSet, "ds_senha") && resultSet.getString("ds_senha") != null) {
			user.setPassword(resultSet.getString("ds_senha"));
		}
		
		int inactiveTime = resultSet.getInt("qt_tempo_inatividade");
		if (resultSet.wasNull()) { 
		    user.setInactiveTime(null);
		} else {
		    user.setInactiveTime(inactiveTime);
		}
		return user;
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
	public boolean isUsernameAvailable(Long id, String username) {
		String sql = "SELECT COUNT(*) "
				+ "FROM usuario WHERE cd_usuario != ? AND nm_login = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.setString(2, username);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
	                return resultSet.getInt(1) == 0;
	            }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updatePassword(Long userId, String newPassword) {
		String sql = "UPDATE usuario SET ds_senha = ? WHERE cd_usuario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, newPassword);
			statement.setLong(2, userId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
