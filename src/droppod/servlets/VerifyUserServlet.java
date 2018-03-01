package droppod.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import droppod.dao.UserDao;

public class VerifyUserServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean status = false;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String uuid = request.getParameter("uuid");
		status = UserDao.verifyUser(uuid);
		if(status) {
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");	//Return the user to the sign in page  
	        rd.forward(request,response); 			//forward the request and response 
		}else {
			out.print("<p style=\"color:red\">Sorry there was an error processing your current request.</p>");  //Let the user know there was a problem processing the request
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  //Return the user to the sign in page
            rd.include(request,response);
		}
	}
}
