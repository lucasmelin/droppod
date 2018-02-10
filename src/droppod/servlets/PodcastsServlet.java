package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import droppod.listen.ListenDroppod;
import droppod.models.EpisodeModel;

public class PodcastsServlet extends HttpServlet{

    private static final long serialVersionUID = 434245632236543L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        String uuid=request.getParameter("uuid");  
        
        HttpSession session = request.getSession(false);
        if(session!=null)
        session.setAttribute("uuid", uuid);
        
        List<EpisodeModel> episodes = ListenDroppod.getEpisodes(uuid);

        if(!episodes.isEmpty()){
        	request.setAttribute("episodes", episodes);
            RequestDispatcher rd=request.getRequestDispatcher("episode.jsp");  
            rd.forward(request,response);  
        }  
        else{  
            out.print("<p style=\"color:red\">Sorry, no such podcast found</p>");  
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
            rd.include(request,response);  
        }  

        out.close();  
    }  
} 