package com.blog.bd;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	private String drive = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/blog";	
	private String user = "root";
	private String pass = "juninho123";
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName(drive);
			conn  = DriverManager.getConnection(url,user,pass);
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return conn;
		}
	}
	
	public void testConnection() {
		try {
			Connection conn = getConnection();
			System.out.println("Conection Success");
			System.out.println(conn);
		} catch (Exception e) {
			System.out.println("ERROR: "+e);
		}
	}
}
