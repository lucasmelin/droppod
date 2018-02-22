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

		int success = 0;
		try {

			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			// DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
			conn = ds.getConnection();

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