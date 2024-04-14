package br.com.bryan.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
	private static final String URL = "jdbc:mysql://localhost:3306/avaliacao";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "admin";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}
