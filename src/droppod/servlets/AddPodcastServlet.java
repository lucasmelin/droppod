package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rometools.modules.itunes.FeedInformation;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;


public class AddPodcastServlet extends HttpServlet{

    private static final long serialVersionUID = 102831973239L;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        String podcastUrl = request.getParameter("podcasturl");  
        
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
        List<SyndEntry> episodes = feed.getEntries();
        Date lastPublished = episodes.get(0).getPublishedDate();
 
        out.print("<p style=\"color:blue\">"+podcastTitle+"</p>");
        out.print("<img src=\""+podcastImageLink.toString()+"\" height=\"200\" width=\"200\">");
        out.print("<p style=\"color:blue\">"+podcastDescription+"</p>"); 
        out.print("<p style=\"color:blue\">"+lastPublished.toString()+"</p>"); 
        RequestDispatcher rd=request.getRequestDispatcher("addPodcast.jsp");  
        rd.include(request,response); 

        out.close();  
    }  
}