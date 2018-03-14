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

public class SearchResult extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Set responses MIME type
		response.setContentType("text/html;charset=UTF-8");

		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<PodcastModel> rows = new ArrayList<PodcastModel>();
		String searchEntry = request.getParameter("search");
		try {

			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			Connection con = ds.getConnection();

			pst = con.prepareStatement("SELECT p.*, pt.podcast_name as name, pt.podcast_description as description " 
			     +"FROM droppod.podcasts p "
			     +"INNER JOIN droppod.podcasts_translations pt ON p.id = pt.podcast_id "
			     +"WHERE pt.podcast_name LIKE ?");

			pst.setString(1, searchEntry+'%');
			rs = pst.executeQuery();

			while (rs.next()) {
				PodcastModel pod = new PodcastModel();
				pod.setUrl(rs.getURL("url"));
				pod.setName(rs.getString("name"));
				pod.setDescription(rs.getString("description"));
				pod.setThumbnail_url(rs.getURL("thumbnail_url"));
				pod.setUuid(rs.getString("uuid"));
				rows.add(pod);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		request.setAttribute("search", rows);
		request.setAttribute("searchEntry", searchEntry);
		request.getRequestDispatcher("searchResult.jsp").forward(request, response);

	}

}
