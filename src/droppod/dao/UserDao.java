package droppod.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDao {
	public static boolean add(String name, String pass, String email) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

<<<<<<< HEAD
int a = 0;
try {			
	
	Context envContext = new InitialContext();
    Context initContext  = (Context)envContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");		//Connect to the database using connection pool
    conn = ds.getConnection();
	
	pst = conn.prepareStatement("insert into droppod.users"+"(username,password,email,validated) values"+"(?,?,?,?)"); //Generate the prepared statement
	pst.setString(1, name);		//Take the username passed from the jsp and input it into the statement
	pst.setString(2, pass);		//Take the password and put it into the prepared statement
	pst.setString(3, email);	//Take the email and put it into the prepared statement
	pst.setInt(4, 0);			//Set the user to unvalidated
	a = pst.executeUpdate();	//Execute the prepared statement with all the values passed in by the jsp
=======
		int success = 0;
		try {

			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			// DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
			conn = ds.getConnection();
>>>>>>> 47d09deddab2a1a0c8622a45aabb6850c0bd34dd

			pst = conn.prepareStatement(
					"insert into droppod.users" + "(username,password,email,validated) values" + "(?,?,?,?)");
			pst.setString(1, name);
			pst.setString(2, pass);
			pst.setString(3, email);
			pst.setInt(4, 0);
			success = pst.executeUpdate();

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
		if (success == 1) {
			return true;
		} else {
			return false;
		}
	}
}
<<<<<<< HEAD
	if(a==1) {		//If the return value from the execution of the prepared statement went through, return true
		return true;
	}else {			//If there was an error performing the statement, return false
		return false;
	}
}
}
=======
>>>>>>> 47d09deddab2a1a0c8622a45aabb6850c0bd34dd
