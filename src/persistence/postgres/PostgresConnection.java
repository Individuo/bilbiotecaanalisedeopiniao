package persistence.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {

	private static PostgresConnection instance;
	private static Connection con;

	private static final String USER = "postgres";
	private static final String PASSWORD = "spf@";
	private static final String STRING_CONNECTION = "jdbc:postgresql://localhost:5432/dadoslol";

	private PostgresConnection() {
	}

	public static PostgresConnection getInstance() {
		if (PostgresConnection.instance == null) {
			PostgresConnection.instance = new PostgresConnection();
		}
		return PostgresConnection.instance;
	}

	public Connection getConnection() {
		try {
			if (PostgresConnection.con == null) {
				PostgresConnection.con = DriverManager.getConnection(STRING_CONNECTION, USER, PASSWORD);
			}
			return con;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
