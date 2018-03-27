package droppod.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import droppod.models.UserModel;


public class UserEditServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		ArrayList<UserModel> users = new ArrayList<UserModel>();
		String id = request.getParameter("id");
		String active = request.getParameter("active");
		String account_type_id = request.getParameter("account_type_id");

		try {

			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn = ds.getConnection();
			pst = conn.prepareStatement("UPDATE droppod.users SET active = ?, account_type_id = ? WHERE id = ?");
			pst.setString(1, active);
			pst.setString(2, account_type_id);
			pst.setString(3, id);
			pst.executeUpdate();

			pst2 = conn.prepareStatement("SELECT * FROM droppod.users WHERE id = ?");
			pst2.setString(1, id);
			rs = pst2.executeQuery();
			
			while (rs.next()) {
				UserModel user = new UserModel(rs.getInt("id"), rs.getString("username"), 
											   rs.getString("email"), rs.getInt("active"), 
											   rs.getInt("account_type_id"));
				users.add(user);
			}

			request.setAttribute("users", users);
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			
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
			if (pst2 != null) {
				try {
					pst2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
