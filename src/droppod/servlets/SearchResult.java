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
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import droppod.models.PodcastModel;

public class SearchResult extends HttpServlet{
	
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response)
             throws IOException, ServletException {
		 //Set responses MIME type
		 response.setContentType("text/html;charset=UTF-8");
		 
		 //Allocate a output writer to write the response message into the network socket
		//PrintWriter out = response.getWriter();
		 
		// request.getRequestDispatcher("/WEB-INF/searchResult.jsp").forward(request, response);
		 PreparedStatement pst = null;
		 ResultSet rs = null;
		 ArrayList<PodcastModel> rows = new ArrayList<PodcastModel>();
		 String searchEntry = request.getParameter("search");
		 try {
			 
	        	Context envContext = new InitialContext();
	            Context initContext  = (Context)envContext.lookup("java:/comp/env");
	            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
	            Connection con = ds.getConnection();
	                         
	          pst = con.prepareStatement("SELECT id, name, description, thumbnail_url, url from droppod.podcasts WHERE name LIKE ?");

	           pst.setString(1, searchEntry + "%");
	            rs = pst.executeQuery();

	            while(rs.next()) {
	            	PodcastModel pod = new PodcastModel();
	            	pod.setUrl(rs.getURL("url"));
	            	pod.setName(rs.getString("name"));
	            	pod.setDescription(rs.getString("description"));
	            	pod.setThumbnail_url(rs.getURL("thumbnail_url"));
	            	rows.add(pod);
	               /*	ArrayList<String> row = new ArrayList<String>();
	               	row.add(rs.getString("id"));
	               	row.add(rs.getString("name"));
	               	row.add(rs.getString("description"));
	               	row.add(rs.getString("thumbnail_url"));
	               	rows.add(row);*/
	            	
	              
	               }
	         
	            //success = pst.executeUpdate();
			 
	    /*     out.println("<!DOCTYPE html>");
	         out.println("<html><head>");
	         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
	         out.println("<title>Hello, World</title></head>");
	         out.println("<body>");
	         // Echo client's request information
	         for(ArrayList<String> list: rows) {
				 out.println("<h1>l</h1>");

				 for (String row: list) {
			 out.println("<h4>"+row+"</h4>");

				 }
				 out.println("<br>");

			 }
	         out.println("<p>Request search parameter: " +searchEntry+ "</p>");

	         out.println("<p>Protocol: " + request.getProtocol() + "</p>");
	         out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
	         out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
	         // Generate a random number upon each request
	         out.println("</body>");
	         out.println("</html>");*/
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	         //out.close();  // Always close the output writer
	      }
		 

			request.setAttribute("search", rows); //categorylist is an arraylist      contains object of class category  
			request.setAttribute("searchEntry", searchEntry);
			request.getRequestDispatcher("searchResult.jsp").forward(request, response);
		//rd.forward(request, response);
       //rd.include(request,response);  
        // out.close();
	     // response.sendRedirect("searchResult.jsp");

		   
		  //request.setAttribute("search", rows);
	       /*RequestDispatcher rd=request.getRequestDispatcher("searchResult.jsp");  


	        rd.forward(request, response);*/
		 
		 
	 }
	

}
