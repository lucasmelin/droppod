package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public class DroppodDAO {
	
	public int add(String name, String pass){

boolean status = false;
Connection conn = null;
PreparedStatement pst = null;
ResultSet rs = null;

String url = "jdbc:mysql://localhost:3306/";
String dbName = "form";
String driver = "com.mysql.jdbc.Driver";
String userName = "root";
String password = "Zisvo_5M";
int a = 0;
try {
	Class.forName(driver).newInstance();
	conn = DriverManager.getConnection(url + dbName, userName, password);

	pst = conn.prepareStatement("insert into login"+"(user,password) values"+"(?,?)");
	pst.setString(1, name);
	pst.setString(2, pass);

	a = pst.executeUpdate();
	//status = rs.next();

} catch (Exception e) {
	System.out.println(e);

} finally {
	if (conn != null) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if (pst != null) {
		try {
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if (rs != null) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
return a;
}
}
