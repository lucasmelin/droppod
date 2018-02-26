package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import droppod.models.EpisodeModel;

/**
 * Parses a given URL to determine if it corresponds to a podcast. If so,
 * podcast info is parsed and added to the database if it does not yet exist.
 * @author Lucas
 *
 */
public class AddPodcastServlet extends HttpServlet{

    private static final long serialVersionUID = 102831973239L;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        /* Get URL passed in from the form*/
        String podcastUrl = request.getParameter("podcasturl");  
        
        /* Set the podcast url in the session */
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("podcasturl", podcastUrl);
        
        URL feedSource = new URL(podcastUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
			feed = input.build(new XmlReader(feedSource));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("<p style=\"color:red\">Invalid podcast url</p>");  
		}
        
        Module module = feed.getModule("http://www.itunes.com/dtds/podcast-1.0.dtd");
        FeedInformation feedInfo = (FeedInformation) module;
        String podcastTitle = feed.getTitle();

        // If the iTunes format image is present, use that one
        URL podcastImageLink = feedInfo.getImage();;
        if (podcastImageLink == null) {
        	// Otherwise, fallback to the image embedded in the feed
        	podcastImageLink = new URL(feed.getImage().getUrl());
        }
        
        String podcastDescription = feed.getDescription();
        /* Basic HTML sanitation*/
        podcastDescription.replaceAll("\\<[^>]*>","");
        List<SyndEntry> episodes = feed.getEntries();
        Date lastPublished = episodes.get(0).getPublishedDate();
        String podcastUri = feed.getUri();
        if (podcastUri == null) {
        	podcastUri = podcastUrl;
        }
        
        /*
         * Add the podcast to the database if it doesn't yet exist
         */
        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        PreparedStatement pst4 = null;
        ResultSet rs = null;
        int success = 0;

        try {

        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
            //DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
            Connection con = ds.getConnection();
                         
            pst = con
            		.prepareStatement("INSERT INTO droppod.podcasts (name, description, url, uri, thumbnail_url) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, podcastTitle);
            pst.setString(2, podcastDescription);
            pst.setString(3, podcastUrl);
            pst.setString(4, podcastUri);
            pst.setString(5, podcastImageLink.toString());
            success = pst.executeUpdate();
            
            /* If the podcast was valid, add the episodes */
            if (success == 1) {
            	/* Get the podcast_id foreign key and place it in a variable for reuse */
            	pst3 = con.prepareStatement("SELECT id FROM droppod.podcasts WHERE name=? LIMIT 1");
            	pst3.setString(1, podcastTitle);
            	rs = pst3.executeQuery();
            	/* Advance the cursor */
            	rs.next();
            	int podcast_id = rs.getInt("id");
            	
            	
            	/* Loop through each episode and add them to the db */
            	pst2 = con
            			.prepareStatement("INSERT INTO droppod.episodes (url, podcast_id, release_date)" + 
            					"VALUES	(?, ?, ?)");
            	
            	pst4 = con.prepareStatement("INSERT INTO droppod.episodes_translations (episode_id, language_code, episode_name, episode_description) VALUES (LAST_INSERT_ID(), ?, ?, ?)");
            	
            	for (SyndEntry ep : episodes) {
            		pst2.setString(1, ep.getEnclosures().get(0).getUrl());
            		pst2.setInt(2, podcast_id);
            		pst2.setTimestamp(3, new java.sql.Timestamp(ep.getPublishedDate().getTime()));

            		pst2.executeUpdate();
            		
            		pst4.setString(1, "en");
            		pst4.setString(2, ep.getTitle());
            		pst4.setString(3, ep.getDescription().getValue());
            		
            		pst4.executeUpdate();
            	}
            	
            	/* Get the episode_id foreign key and place it in a variable for reuse*/
            }

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
 
        /* Display the podcast info on the page */
        StringBuilder message = new StringBuilder();
        message.append("<p style=\"color:blue\"> Status: "+success+"</p>");
        message.append("<p style=\"color:blue\">"+podcastTitle+"</p>");
        message.append("<img src=\""+podcastImageLink.toString()+"\" height=\"200\" width=\"200\">");
        message.append("<p style=\"color:blue\">"+podcastDescription+"</p>"); 
        message.append("<p style=\"color:blue\">"+lastPublished.toString()+"</p>");
        request.setAttribute("message", message.toString());
        RequestDispatcher rd=request.getRequestDispatcher("addPodcast.jsp");  
        rd.include(request,response); 

        out.close();  
    }  
}