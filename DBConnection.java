package com.bank.jdbc;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DBConnection {
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/bank";
	private static final String userId = "root";
	private static final String password = "1434";
	
	private static Connection connection = null;
	static{
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userId, password);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}
}