package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import droppod.login.LoginDroppod;

public class DeleteUserServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		Connection conn = null;
		PreparedStatement pst = null;
		int success = 0;
		String name = request.getParameter("username");
		String status = null;

		try {

			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			conn = ds.getConnection();

			pst = conn.prepareStatement("DELETE from droppod.users where username=?");
			pst.setString(1, name);
			success = pst.executeUpdate();

			if (success != 0) {
				status = "User " + name + " deleted";
			} else {
				status = "failed to remove " + name + " from database";
			}
			request.setAttribute("operationStatus", status);
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
