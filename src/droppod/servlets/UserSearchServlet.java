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

public class UserSearchServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String name = request.getParameter("username");
		String id = request.getParameter("id");
		ArrayList<UserModel> users = new ArrayList<UserModel>();

		try {

			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn = ds.getConnection();

			if (name != null && id == null) {
				pst = conn.prepareStatement("SELECT * FROM droppod.users WHERE username LIKE ?");
				pst.setString(1, '%'+name+'%');
				rs = pst.executeQuery();
				
				while (rs.next()) {
					UserModel user = new UserModel(rs.getInt("id"), rs.getString("username"), 
												   rs.getString("email"), rs.getInt("active"), 
												   rs.getInt("account_type_id"));
					users.add(user);
				}

				request.setAttribute("users", users);
			} else if (name == null && id != null) {
				pst = conn.prepareStatement("SELECT * FROM droppod.users WHERE id = ?");
				pst.setString(1, id);
				rs = pst.executeQuery();
				rs.next();
				
				UserModel user = new UserModel(rs.getInt("id"), rs.getString("username"), 
						   rs.getString("email"), rs.getInt("active"), 
						   rs.getInt("account_type_id"));

				request.setAttribute("user", user);
			}
			
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

		}
	}
}
