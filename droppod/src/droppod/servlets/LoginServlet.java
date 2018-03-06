package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import droppod.login.LoginDroppod;

public class LoginServlet extends HttpServlet{

    private static final long serialVersionUID = 213123L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        String n=request.getParameter("username");  
        String p=request.getParameter("password"); 
        int userID = 0;
        //int [] podcastIDs = null;
        String[] podcastIDs;
        
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("name", n);

        if(LoginDroppod.validate(n, p)){  
            RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");  
            rd.forward(request,response);  
        }  
        else{  
            out.print("<p style=\"color:red\">Sorry username or password error</p>");  
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
            rd.include(request,response);  
        } 
        
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
        	
        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");          
            con = ds.getConnection();
            
           /* Get podcast_ids by using the username */
            pst = con.prepareStatement("SELECT uuid FROM droppod.podcasts WHERE id IN (SELECT podcast_id FROM droppod.user_follows WHERE user_id IN (SELECT id FROM droppod.users WHERE username=?))");
            pst.setString(1, n);
            
            rs = pst.executeQuery();
            //rs.next();
            rs.last();
            int rowCount = rs.getRow();
            podcastIDs = new String[rowCount];
            rs.first();
            //rs.next();
            for (int i = 0; i < rowCount; i++) {
            	System.out.println(rs.getString(1));
            	podcastIDs[i] = rs.getString(1);
            	rs.next();
            }
            
            session.setAttribute("podcastIDs", podcastIDs);
       
            
        } catch (Exception e) {
        	System.out.println("Exception: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
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
        

        out.close();  
    }  
} 