package bbs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import bbs.exception.SQLRuntimeException;

public class DBUtil {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/bbs";
	private static final String USER = "root";
	private static final String PASSWORD = "w3Ui4dZA";

	static {

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {

		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

			connection.setAutoCommit(false);

			return connection;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	public static void commit(Connection connection) {

		try {
			connection.commit();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	public static void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
