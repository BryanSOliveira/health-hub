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

@Singleton
public class UserDAOImpl implements UserDAO {

	@Override
	public User findById(Long id) {
		String sql = "SELECT * FROM usuario WHERE cd_usuario = ?";
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
	public void save(User user) {
		String sql = "INSERT INTO usuario (nm_login, ds_senha) VALUES (?, ?)";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE usuario SET nm_login = ?, ds_senha = ?, qt_tempo_inatividade = ? WHERE cd_usuario = ?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getInactiveTime());
			statement.setLong(4, user.getId());
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
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users";
		try (Connection connection = DataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				User user = mapResultSetToUser(resultSet);
				users.add(user);
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
		user.setPassword(resultSet.getString("ds_senha"));
		user.setInactiveTime(resultSet.getInt("qt_tempo_inatividade"));
		return user;
	}

	@Override
	public User findByUsername(String username) {
		String sql = "SELECT * FROM usuario WHERE nm_login = ?";
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
}
