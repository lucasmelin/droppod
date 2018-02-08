package droppod.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import droppod.dao.UserDao;
import droppod.login.LoginDroppod;

public class SignUpServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String n=request.getParameter("username");  
        String p=request.getParameter("password");
        String e=request.getParameter("email");
        
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("name", n);

        if(UserDao.add(n,p,e)){ 
        	System.out.print("USER WAS ADDED");
        	out.print("<p>User was added.</p>");
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
            rd.forward(request,response);  
        }  
        else{  
        	System.out.print("USER WASNT ADDED");
            out.print("<p style=\"color:red\">Sorry there was an error processing your request.</p>");  
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
            rd.include(request,response);  
        }  

        out.close();  
	}
}
