package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import droppod.listen.ListenDroppod;
import droppod.listen.TranslateDroppod;
import droppod.models.EpisodeModel;
import droppod.models.PodcastModel;

import com.google.api.services.translate.Translate.Translations;
//Imports the Google Cloud client library
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


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
        
    	// If the session language is not english, we'll
    	// need to translate the episode information before returning
    	Locale userLocale = Locale.ENGLISH;
    	String userLang = session.getAttribute("language").toString();
    	if (userLang != null) {
    		userLocale = new Locale(userLang);
    	}
        
        PodcastModel podcast = ListenDroppod.getPodcast(uuid);
        List<EpisodeModel> episodes = ListenDroppod.getEpisodes(uuid, userLocale);
        
        // Make sure that we have episode information to return
        if(!episodes.isEmpty()){
        	
/*        	// Verify that we do actually need to translate our strings
        	userLang = userLocale.getLanguage();
        	if (!(userLang.startsWith(Locale.ENGLISH.getLanguage()))) {
				// Make sure we're configured to access the translate API
        		if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") != null) {
        			// Instantiate a client
					Translate translate = TranslateOptions.getDefaultInstance().getService();
	
					List<String> episodeNames = new ArrayList<String>();
					List<String> episodeDescriptions = new ArrayList<String>();
					// Extract the descriptions and names from each podcast episode
					for (EpisodeModel e : episodes) {
						episodeNames.add(e.getName());
						episodeDescriptions.add(e.getDescription());
					}
	
					// Translate the text into the session language
					List<Translation> translatedNames = translate.translate(episodeNames,
							TranslateOption.sourceLanguage(Locale.ENGLISH.getLanguage()),
							TranslateOption.targetLanguage(userLocale.getLanguage()));
					List<Translation> translatedDescriptions = translate.translate(episodeDescriptions,
							TranslateOption.sourceLanguage(Locale.ENGLISH.getLanguage()),
							TranslateOption.targetLanguage(userLocale.getLanguage()));
					
					// Insert new translations into the database
					TranslateDroppod.addEpisodeTranslation(userLocale, episodes, translatedNames, translatedDescriptions);					
					
	
					// Replace the descriptions and names in each podcast episode
					for (int i = 0; i < episodes.size(); i++) {
						episodes.get(i).setName(translatedNames.get(i).getTranslatedText());
						episodes.get(i).setDescription(translatedDescriptions.get(i).getTranslatedText());
					}
        		}

        	}*/
        	request.setAttribute("podcast", podcast);
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