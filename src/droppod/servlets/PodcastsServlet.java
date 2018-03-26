package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import droppod.listen.ListenDroppod;
import droppod.models.EpisodeModel;
import droppod.models.GeolocationModel;
import droppod.models.PodcastModel;


public class PodcastsServlet extends HttpServlet{

    private static final long serialVersionUID = 434245632236543L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();  
        
        String uuid=request.getParameter("uuid");  
        
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("uuid", uuid);
        
    	// If the session language is not english, we'll
    	// need to translate the episode information before returning
    	Locale userLocale = Locale.ENGLISH;
    	String userLang = session.getAttribute("language").toString();
    	if (userLang != null) {
    		userLocale = new Locale(userLang);
    	}
        
        PodcastModel podcast = ListenDroppod.getPodcast(uuid, userLocale);
        List<EpisodeModel> episodes = ListenDroppod.getEpisodes(uuid, userLocale);
        List<GeolocationModel> followers = ListenDroppod.getFollowers(uuid);
        // Make sure that we have episode information to return
        if(!episodes.isEmpty()){
        	
        	request.setAttribute("podcast", podcast);
        	request.setAttribute("episodes", episodes);
        	request.setAttribute("followers", followers);
            RequestDispatcher rd=request.getRequestDispatcher("episode.jsp");  
            rd.forward(request,response);  
        }  
        else{  
            out.print("<p style=\"color:red\">Sorry, no such podcast found</p>");  
            RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");  
            rd.include(request,response);  
        }  

        out.close();  
    }  
} 