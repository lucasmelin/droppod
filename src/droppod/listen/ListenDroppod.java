package droppod.listen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

import droppod.models.EpisodeModel;
import droppod.models.PodcastModel;

public class ListenDroppod {
	public static List<EpisodeModel> getEpisodes(String uuid, Locale userLocale) {        

        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        List<EpisodeModel> rows = new ArrayList<EpisodeModel>();
        

        try {

        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
            //DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
            Connection con = ds.getConnection();
            // We're treating all variants of language as the same by doing this.
            // Ie: Canadian English is the same as US English
            String returnLanguage = userLocale.getLanguage().substring(0, 2);

            pst = con.prepareStatement("SELECT e.*, et.episode_name as name, et.episode_description as description "
            		+ "FROM droppod.episodes e "
            		+ "INNER JOIN droppod.episodes_translations et ON e.id = et.episode_id "
            		+ "WHERE e.podcast_id IN (SELECT id FROM droppod.podcasts WHERE uuid=?) "
            		+ "AND et.language_code=?;"); // and et.language_code=?
            
            pst.setString(1, uuid);
            pst.setString(2, returnLanguage);
            rs = pst.executeQuery();
            
            // If result set is empty, this means that the descriptions and names are not available in the
            // target language. Therefore, we'll retrieve the default (english) strings, and translate them instead.
            if (!rs.isBeforeFirst() ) {    
            	pst2 = con.prepareStatement("SELECT e.*, et.episode_name as name, et.episode_description as description "
                		+ "FROM droppod.episodes e "
                		+ "INNER JOIN droppod.episodes_translations et ON e.id = et.episode_id "
                		+ "WHERE e.podcast_id IN (SELECT id FROM droppod.podcasts WHERE uuid=?) "
                		+ "AND et.language_code='en';");
                
            	pst2.setString(1, uuid);
                rs2 = pst2.executeQuery();
               // TODO: Query for the english strings, then call the appropriate TranslateDroppod method.
                List<String> episodeNames = new ArrayList<String>();
    			List<String> episodeDescriptions = new ArrayList<String>();
    			/* Get all the rows from the result set and put them in an ArrayList so
                 * that we can close the DB connection. */
                while(rs2.next()) {
                	EpisodeModel episode = new EpisodeModel();
                	episode.setId(rs2.getInt("id"));
                	// Extract the descriptions and names from each podcast episode
                	episodeNames.add(rs2.getString("name"));
                	episodeDescriptions.add(rs2.getString("description"));
                	episode.setUrl(rs2.getURL("url"));
                	rows.add(episode);
                }
                
                //Translate the names and descriptions into the session language
                // Make sure we're configured to access the translate API
                
        		if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") != null) {
	                // Instantiate a client
					Translate translate = TranslateOptions.getDefaultInstance().getService();
					
					
					List<Translation> translatedNames = translate.translate(episodeNames,
							TranslateOption.sourceLanguage(Locale.ENGLISH.getLanguage()),
							TranslateOption.targetLanguage(userLocale.getLanguage()));
					List<Translation> translatedDescriptions = translate.translate(episodeDescriptions,
							TranslateOption.sourceLanguage(Locale.ENGLISH.getLanguage()),
							TranslateOption.targetLanguage(userLocale.getLanguage()));
					
					// Insert new translations into the database
					TranslateDroppod.addEpisodeTranslation(userLocale, rows, translatedNames, translatedDescriptions);					
					
	
					// Replace the descriptions and names in each podcast episode
					for (int i = 0; i < rows.size(); i++) {
						rows.get(i).setName(translatedNames.get(i).getTranslatedText());
						rows.get(i).setDescription(translatedDescriptions.get(i).getTranslatedText());
					}
        		}
    			
    			
            } else {
            	/* Get all the rows from the result set and put them in an ArrayList so
                 * that we can close the DB connection. */
                while(rs.next()) {
                	EpisodeModel episode = new EpisodeModel();
                	episode.setId(rs.getInt("id"));
                	episode.setName(rs.getString("name"));
                	episode.setDescription(rs.getString("description"));
                	episode.setUrl(rs.getURL("url"));
                	rows.add(episode);
                }
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rows;
    }
    
    
    
    
    public static PodcastModel getPodcast(String uuid) {        

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PodcastModel podcast = null;


        try {

        	Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
            //DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
            Connection con = ds.getConnection();
                         
            pst = con
            		.prepareStatement("SELECT * FROM droppod.podcasts WHERE uuid=?");
            pst.setString(1, uuid);
            rs = pst.executeQuery();
            
            /* Get all the rows from the result set and put them in an ArrayList so
             * that we can close the DB connection. */
            while(rs.next()) {
            	podcast = new PodcastModel();
            	podcast.setName(rs.getString("name"));
            	podcast.setDescription(rs.getString("description"));
            	podcast.setUrl(rs.getURL("url"));
            	podcast.setThumbnail_url(rs.getURL("thumbnail_url"));
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return podcast;
    }
    
  
}
