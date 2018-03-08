package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.rometools.modules.itunes.FeedInformation;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import droppod.listen.ListenDroppod;
import droppod.models.EpisodeModel;
import droppod.models.PodcastModel;

/**
 * Parses a given URL to determine if it corresponds to a podcast. If so,
 * podcast info is parsed and added to the database if it does not yet exist.
 * @author Lucas
 *
 */
public class UnfollowPodcastServlet extends HttpServlet{

	private static final long serialVersionUID = 434245632236543L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  

    	response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        HttpSession session = request.getSession(false);
        
        String uuid = session.getAttribute("uuid").toString();
        String username = session.getAttribute("name").toString();
             
        int userID = 0;
        
        Connection con = null;
        PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;

        try {
        	
        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");          
            con = ds.getConnection();
            
            /* Query 1 for getting userID of current user */
            pst = con.prepareStatement("SELECT id FROM droppod.users WHERE username=?");
            pst.setString(1, username);
            
            rs = pst.executeQuery();
            rs.next();
            userID = rs.getInt(1);
            
          
           /* Get podcast id by using the uuid */
            pst = con.prepareStatement("SELECT id FROM droppod.podcasts WHERE uuid=?");
            pst.setString(1, uuid);
            System.out.println(uuid);
            rs = pst.executeQuery();
            rs.next();
            int podcastID = rs.getInt(1);
            
           
            
            
            /* Query 2 for inserting userID and podcastID into user_follows table */
            pst2 = con.prepareStatement("DELETE FROM droppod.subscriptions WHERE podcast_id IN (SELECT id FROM droppod.podcasts where uuid =?) AND user_id = ?");  
            pst2.setString(1, uuid);
            pst2.setInt(2, userID);
        	
            if (pst2.executeUpdate() == 1) {
            	
            	String[] temp = (String[]) session.getAttribute("podcastIDs");
            	
            	String[] newPod = new String[temp.length - 1];
            	
            	System.out.println("temp Length: " + temp.length);
            	System.out.println("New Length: " + newPod.length);

            	
            	
            	for (int i = 0, j = 0; i < temp.length; i++) {
            		if (temp[i].compareTo(uuid) != 0) {
            			newPod[j] = temp[i];
            			j++;
            		}
            	}
            	
            	for (int i = 0; i < newPod.length; i++) {
            		System.out.println(newPod[i]);
            	}
            	
            	session.setAttribute("podcastIDs", newPod);
            	
            } else {
            	System.out.println("YOU SUCK");
            }
            
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

            RequestDispatcher rd=request.getRequestDispatcher("following.jsp");  
            rd.include(request,response); 
            
            out.close();  
        } 
    }
}