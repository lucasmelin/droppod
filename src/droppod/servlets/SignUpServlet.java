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
		response.setContentType("text/html");		//Set the content of the response to text
		PrintWriter out = response.getWriter();
		
		String n=request.getParameter("username");  //Get the username parameter from the request
        String p=request.getParameter("password");	//Get the password parameter from the request
        String e=request.getParameter("email");		//Get the email parameter from the request
        String p2=request.getParameter("repassword");//Get the retyped password from the request
        
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("name", n);

        if(p.equals(p2) && UserDao.add(n,p,e)){ 	//If the retyped password is equal to the password and the database query was performed properly
        	out.print("<p>User was added.</p>");	//print user was added
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");	//Return the user to the sign in page  
            rd.forward(request,response); 			//forward the request and response 
        }  
        else{  										//If there was an error performing the sql statement
            out.print("<p style=\"color:red\">Sorry there was an error processing your request.</p>");  //Let the user know there was a problem processing the request
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  //Return the user to the sign in page
            rd.include(request,response);  
        }  

        out.close();  //Close the printwriter
	}
}
