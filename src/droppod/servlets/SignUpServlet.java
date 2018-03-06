package droppod.servlets;

import droppod.bcrypt.BCrypt;

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
		
		String name = request.getParameter("username");  
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String hashedpassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        
        HttpSession session = request.getSession(false);
        
        if(session!=null)
        session.setAttribute("name", name);

        if(password.equals(repassword) && UserDao.add(name, hashedpassword, email)){ 
        	out.print("<p>User was added.</p>");
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
            rd.forward(request,response);  
        }  
        else{ 
        	request.setAttribute("success", "false");
            RequestDispatcher rd=request.getRequestDispatcher("signUp.jsp");  
            rd.include(request,response);  
        }  

        out.close();  
	}
}
