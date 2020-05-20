package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass {
	public static ResultSet resultSet;
	public static Statement statement;
	public static java.sql.PreparedStatement preparedStatement;

	public ConnectionClass() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_project", "root",
					"tiger");
			
			System.out.println("Connected");

			statement = connection.createStatement();

			preparedStatement = connection.prepareStatement("insert into customer_details values (?,?,?,?,?,?,?,?,?,?,?)");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static java.sql.PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public static void setPreparedStatement(java.sql.PreparedStatement preparedStatement) {
		ConnectionClass.preparedStatement = preparedStatement;
	}

	public static ResultSet getResultSet() {
		return resultSet;
	}

	public static void setResultSet(ResultSet resultSet) {
		ConnectionClass.resultSet = resultSet;
	}

	public static Statement getStatement() {
		return statement;
	}

	public static void setStatement(Statement statement) {
		ConnectionClass.statement = statement;
	}

}
