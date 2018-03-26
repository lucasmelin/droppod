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

public class PopularPodcastsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Set responses MIME type
		response.setContentType("text/html;charset=UTF-8");

		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ArrayList<PodcastModel> rows = new ArrayList<PodcastModel>();
		int cityId;
		int countryId;
		String languageCode = null;
		String cityName = null;
		
		try {

			Context envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
			Connection con = ds.getConnection();
			HttpSession session = request.getSession(false);
			String username = (String)session.getAttribute("name");
			
		//pst1 gets user information for finding podcasts in their city
			pst1 = con.prepareStatement("SELECT users.city_id, users.country_id, cities.name, languages.code\r\n" + 
					"from droppod.users\r\n" + 
					"left join droppod.countries on users.country_id = countries.id\r\n" + 
					"left join droppod.cities on users.city_id = cities.id\r\n" + 
					"left join droppod.languages on users.language_id = languages.id\r\n" + 
					"where users.username = ?");
			pst1.setString(1, username);
			rs1 = pst1.executeQuery();
			
			 rs1.next();
			cityId = rs1.getInt("city_id");
			countryId = rs1.getInt("country_id");
			languageCode = rs1.getString("code");
			//if the user hasnt set their language, the below sql statment will not return results. This
			//if statement checks for null in the database and sets english as default if so
			if(languageCode ==null) {
				languageCode ="en";
			}
			cityName = rs1.getString("name");

			//pst finds podcasts close to the user
			pst = con.prepareStatement("SELECT p.id, COUNT(s.user_id) AS numusers, pt.podcast_name, pt.podcast_description, p.rating, p.url, p.thumbnail_url, p.uuid\r\n" + 
					"FROM droppod.podcasts AS p\r\n" + 
					"LEFT JOIN droppod.subscriptions AS s ON p.id = s.podcast_id\r\n" + 
					"RIGHT JOIN droppod.users as u on s.user_id =  u.id\r\n" + 
					"RIGHT JOIN droppod.cities as c on u.city_id = c.id\r\n" + 
					"RIGHT JOIN droppod.countries as co on u.country_id = co.id\r\n" +
					"LEFT JOIN droppod.podcasts_translations AS pt ON p.id = pt.podcast_id \r\n" + 
					"WHERE pt.language_code =?\r\n" + 
					"AND c.id =?\r\n" + 
					"AND co.id =?\r\n" + 
					"GROUP BY p.id\r\n" +
					"ORDER BY numusers DESC");

			pst.setString(1, languageCode);
			pst.setInt(2, cityId);
			pst.setInt(3, countryId);

			rs = pst.executeQuery();

			while (rs.next()) {
				PodcastModel pod = new PodcastModel();
				pod.setUrl(rs.getURL("url"));
				pod.setName(rs.getString("podcast_name"));
				pod.setDescription(rs.getString("podcast_description"));
				pod.setThumbnail_url(rs.getURL("thumbnail_url"));
				pod.setSubscriptions(rs.getInt("numusers"));
				rows.add(pod);
			}
		
//set request attribute for number of users
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		
		}

		request.setAttribute("search", rows);
		request.setAttribute("area", cityName);
		request.getRequestDispatcher("/popularPodcasts.jsp").forward(request, response);

	}

}
