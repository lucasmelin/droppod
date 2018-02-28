package droppod.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UuidDao {
	public static String getUuid(String username) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int success = 0;
		String uuid = null;
		try {
			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn = ds.getConnection();
			pst = conn.prepareStatement(
					"SELECT * from droppod.users where username=?");
			pst.setString(1,username);
			rs = pst.executeQuery();
			try {
			uuid = rs.getString("uuid");
			}catch(Exception a) {
				a.printStackTrace();
			}
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
			return uuid;
	}
	
}
