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

import droppod.bcrypt.BCrypt;
import droppod.models.EpisodeModel;
import droppod.models.GeolocationModel;
import droppod.models.PodcastModel;

public class ListenDroppod {
	public static List<EpisodeModel> getEpisodes(String uuid, Locale userLocale) {

	    Connection conn = null;
	    PreparedStatement pstEnglish = null;
	    PreparedStatement pstCompare = null;
	    PreparedStatement pstTranslate = null;
	    PreparedStatement pstFrench = null;
	    ResultSet rsEnglish = null;
	    ResultSet rsCompare = null;
	    ResultSet rsTranslate = null;
	    ResultSet rsFrench = null;
	    int translateEpisodes = 0;
	    int totalEpisodes = 0;
	    List<EpisodeModel> rows = new ArrayList<EpisodeModel>();
	    

	    try {

	      Context envContext = new InitialContext();
	      Context initContext = (Context) envContext.lookup("java:/comp/env");
	      DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
	      // DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
	      Connection con = ds.getConnection();
	      // We're treating all variants of language as the same by doing this.
	      // Ie: Canadian English is the same as US English
	      String returnLanguage = userLocale.getLanguage().substring(0, 2);

	      pstEnglish = con.prepareStatement(
	          "SELECT e.*, et.episode_name as name, et.episode_description as description "
	              + "FROM droppod.episodes e "
	              + "INNER JOIN droppod.episodes_translations et ON e.id = et.episode_id "
	              + "WHERE e.podcast_id IN (SELECT id FROM droppod.podcasts WHERE uuid=?) "
	              + "AND et.language_code=? order by release_date desc;");
	      pstEnglish.setString(1, uuid);
	      pstEnglish.setString(2, returnLanguage);
	      rsEnglish = pstEnglish.executeQuery();
	      
	      pstCompare = con.prepareStatement(
	              "SELECT e.*, count(et.episode_name) as name, et.episode_description as description "
	                      + "FROM droppod.episodes e "
	                      + "INNER JOIN droppod.episodes_translations et ON e.id = et.episode_id "
	                      + "WHERE e.podcast_id IN (SELECT id FROM droppod.podcasts WHERE uuid=?) "
	                      + "AND et.language_code=? order by release_date desc;");
	      pstCompare.setString(1,uuid);
	      pstCompare.setString(2,returnLanguage);
	      rsCompare = pstCompare.executeQuery();
	      
	      if(rsCompare.next()) {
	    	  translateEpisodes = rsCompare.getInt("name");
	      }
	      pstCompare.setString(1,uuid);
	      pstCompare.setString(2,"en");
	      rsCompare = pstCompare.executeQuery();
	      if(rsCompare.next()) {
	    	  totalEpisodes = rsCompare.getInt("name");
	      }
	      // If result set is empty, this means that the descriptions and names are not available in the
	      // target language. Therefore, we'll retrieve the default (english) strings, and translate
	      // them instead.
	      if (!rsEnglish.isBeforeFirst() || totalEpisodes>translateEpisodes) {
	    	  
	        pstTranslate = con.prepareStatement(
	            "SELECT e.*, et.episode_name as name, et.episode_description as description "
	                + "FROM droppod.episodes e "
	                + "INNER JOIN droppod.episodes_translations et ON e.id = et.episode_id "
	                + "WHERE e.podcast_id IN (SELECT id FROM droppod.podcasts WHERE uuid=?) "
	                + "AND et.language_code='en' order by release_date desc;");

	        pstTranslate.setString(1, uuid);
	        rsTranslate = pstTranslate.executeQuery();
	        // TODO: Query for the english strings, then call the appropriate TranslateDroppod method.
	        List<String> episodeNames = new ArrayList<String>();
	        List<String> episodeDescriptions = new ArrayList<String>();
	        /*
	         * Get all the rows from the result set and put them in an ArrayList so that we can close
	         * the DB connection.
	         */
	        while (rsTranslate.next()) {
	          EpisodeModel episode = new EpisodeModel();
	          episode.setId(rsTranslate.getInt("id"));
	          // Extract the descriptions and names from each podcast episode
	          episodeNames.add(rsTranslate.getString("name"));
	          episodeDescriptions.add(rsTranslate.getString("description"));
	          episode.setUrl(rsTranslate.getURL("url"));
	          rows.add(episode);
	        }
	        if(rsEnglish.isBeforeFirst()) {
	          int updateEpisodes = totalEpisodes-translateEpisodes;
	  		  rows = rows.subList(0,updateEpisodes);
	  	  	}
	        // Translate the names and descriptions into the session language
	        // Make sure we're configured to access the translate API

	        if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") != null) {
	            pstFrench = con.prepareStatement(
	                    "SELECT e.*, et.episode_name as name, et.episode_description as description "
	                            + "FROM droppod.episodes e "
	                            + "INNER JOIN droppod.episodes_translations et ON e.id = et.episode_id "
	                            + "WHERE e.podcast_id IN (SELECT id FROM droppod.podcasts WHERE uuid=?) "
	                            + "AND et.language_code=? order by release_date desc;");
	            pstFrench.setString(1,uuid);
	            pstFrench.setString(2,returnLanguage);
	            rsFrench = pstFrench.executeQuery();
	        	// Instantiate a client
	          Translate translate = TranslateOptions.getDefaultInstance().getService();
	          
	          // Translate all the episode names
	          List<Translation> translatedNames = translate.translate(episodeNames,
	              TranslateOption.sourceLanguage(Locale.ENGLISH.getLanguage()),
	              TranslateOption.targetLanguage(userLocale.getLanguage()));
	          
	          // Translate all the episode descriptions
	          List<Translation> translatedDescriptions = translate.translate(episodeDescriptions,
	              TranslateOption.sourceLanguage(Locale.ENGLISH.getLanguage()),
	              TranslateOption.targetLanguage(userLocale.getLanguage()));

	          // Insert new translations into the database
	          TranslateDroppod.addEpisodeTranslation(userLocale, rows, translatedNames,
	              translatedDescriptions);

	          // Replace the descriptions and names in each podcast episode
	          for (int i = 0; i < rows.size(); i++) {
	            rows.get(i).setName(translatedNames.get(i).getTranslatedText());
	            rows.get(i).setDescription(translatedDescriptions.get(i).getTranslatedText());
	          }

	          while (rsFrench.next()) {
	              EpisodeModel episode = new EpisodeModel();
	              episode.setId(rsFrench.getInt("id"));
	              episode.setName(rsFrench.getString("name"));
	              episode.setDescription(rsFrench.getString("description"));
	              episode.setUrl(rsFrench.getURL("url"));
	              rows.add(episode);
	            }
	          
	        }

	      } else {
	        /*
	         * Get all the rows from the result set and put them in an ArrayList so that we can close
	         * the DB connection.
	         */
	        while (rsEnglish.next()) {
	          EpisodeModel episode = new EpisodeModel();
	          episode.setId(rsEnglish.getInt("id"));
	          episode.setName(rsEnglish.getString("name"));
	          episode.setDescription(rsEnglish.getString("description"));
	          episode.setUrl(rsEnglish.getURL("url"));
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
	      if (pstEnglish != null) {
	        try {
	          pstEnglish.close();
	        } catch (SQLException e) {
	          e.printStackTrace();
	        }
	      }
	      if (pstTranslate != null) {
	        try {
	          pstTranslate.close();
	        } catch (SQLException e) {
	          e.printStackTrace();
	        }
	      }
	      if (pstCompare != null) {
	          try {
	            pstCompare.close();
	          } catch (SQLException e) {
	            e.printStackTrace();
	          }
	        }
	      if (pstFrench != null) {
	          try {
	            pstFrench.close();
	          } catch (SQLException e) {
	            e.printStackTrace();
	          }
	        }
	      if (rsEnglish != null) {
	        try {
	          rsEnglish.close();
	        } catch (SQLException e) {
	          e.printStackTrace();
	        }
	      }
	      if (rsTranslate != null) {
	        try {
	          rsTranslate.close();
	        } catch (SQLException e) {
	          e.printStackTrace();
	        }
	      }
	      if (rsCompare != null) {
	          try {
	            rsCompare.close();
	          } catch (SQLException e) {
	            e.printStackTrace();
	          }
	        }
	      if (rsFrench != null) {
	          try {
	            rsFrench.close();
	          } catch (SQLException e) {
	            e.printStackTrace();
	          }
	        }
	    }
	    return rows;
	  }


  public static PodcastModel getPodcast(String uuid, Locale userLocale) {

    Connection conn = null;
    PreparedStatement pstEnglish = null;
    PreparedStatement pstTranslate = null;
    ResultSet rsEnglish = null;
    ResultSet rsTranslate = null;
    PodcastModel podcast = null;


    try {

      Context envContext = new InitialContext();
      Context initContext = (Context) envContext.lookup("java:/comp/env");
      DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
      // DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
      Connection con = ds.getConnection();

      String returnLanguage = userLocale.getLanguage().substring(0, 2);

      pstTranslate = con.prepareStatement(
          "SELECT p.*, pt.podcast_name as name, pt.podcast_description as description "
              + "FROM droppod.podcasts p "
              + "INNER JOIN droppod.podcasts_translations pt ON p.id = pt.podcast_id "
              + "WHERE p.uuid=? " + "AND pt.language_code=?;");
      pstTranslate.setString(1, uuid);
      pstTranslate.setString(2, returnLanguage);
      rsTranslate = pstTranslate.executeQuery();


      // If result set is empty, this means that the descriptions and names are not available in the
      // target language. Therefore, we'll retrieve the default (english) strings, and translate
      // them instead.
      if (!rsTranslate.isBeforeFirst()) {
        pstEnglish = con.prepareStatement(
            "SELECT p.*, pt.podcast_name as name, pt.podcast_description as description "
                + "FROM droppod.podcasts p "
                + "INNER JOIN droppod.podcasts_translations pt ON p.id = pt.podcast_id "
                + "WHERE p.uuid=? " + "AND pt.language_code='en';");

        pstEnglish.setString(1, uuid);
        rsEnglish = pstEnglish.executeQuery();
        // Query for the english strings, then call the appropriate TranslateDroppod method.
        List<String> podcastInfo = new ArrayList<String>();

        while (rsEnglish.next()) {
          podcast = new PodcastModel();
          podcast.setId(rsEnglish.getInt("id"));
          podcastInfo.add(rsEnglish.getString("name"));
          podcastInfo.add(rsEnglish.getString("description"));
          podcast.setUrl(rsEnglish.getURL("url"));
          podcast.setThumbnail_url(rsEnglish.getURL("thumbnail_url"));
        }

        // Translate the names and descriptions into the session language
        // Make sure we're configured to access the translate API

        if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") != null) {
          // Instantiate a client
          Translate translate = TranslateOptions.getDefaultInstance().getService();


          // Translate strings and set the podcast name and description
          List<Translation> translatedInfo = translate.translate(podcastInfo,
              TranslateOption.sourceLanguage(Locale.ENGLISH.getLanguage()),
              TranslateOption.targetLanguage(userLocale.getLanguage()));

          // Save podcast name and description to the database
          String podcastName = translatedInfo.get(0).getTranslatedText();
          String podcastDescription = translatedInfo.get(1).getTranslatedText();
          
          TranslateDroppod.addPodcastTranslation(userLocale, podcast, podcastName, podcastDescription);
          
          podcast.setName(podcastName);
          podcast.setDescription(podcastDescription);

        }
      } else {
        /*
         * Get all the rows from the result set and put them in an ArrayList so that we can close
         * the DB connection.
         */
        while (rsTranslate.next()) {
          podcast = new PodcastModel();
          podcast.setName(rsTranslate.getString("name"));
          podcast.setDescription(rsTranslate.getString("description"));
          podcast.setUrl(rsTranslate.getURL("url"));
          podcast.setThumbnail_url(rsTranslate.getURL("thumbnail_url"));
          podcast.setUuid(uuid);
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
      if (pstEnglish != null) {
        try {
          pstEnglish.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (pstTranslate != null) {
        try {
          pstTranslate.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (rsEnglish != null) {
        try {
          rsEnglish.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (rsTranslate != null) {
        try {
          rsTranslate.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return podcast;
  }

  public static ArrayList<GeolocationModel> getFollowers(String uuid){
	  boolean status = false;
      Connection conn = null;
      PreparedStatement pst = null;
      ResultSet rs = null;
      ArrayList<GeolocationModel> geoList = new ArrayList<GeolocationModel>();
      try {       	
      	Context envContext = new InitialContext();
          Context initContext  = (Context)envContext.lookup("java:/comp/env");
          DataSource ds = (DataSource)initContext.lookup("jdbc/droppod");
          conn = ds.getConnection();
                       
          pst = conn
          		.prepareStatement("SELECT * FROM droppod.cities \r\n" + 
          				"LEFT JOIN droppod.users ON droppod.users.city_id = cities.id \r\n" + 
          				"LEFT JOIN droppod.SUBSCRIPTIONS ON droppod.SUBSCRIPTIONS.USER_ID = droppod.USERS.ID \r\n" + 
          				"LEFT JOIN droppod.PODCASTS ON droppod.PODCASTS.ID = droppod.SUBSCRIPTIONS.PODCAST_ID \r\n" + 
          				"WHERE droppod.PODCASTS.UUID = ?");
          pst.setString(1, uuid);
          rs = pst.executeQuery();
          while(rs.next()) { 
        	  float geolat = rs.getFloat("latitude");
        	  float geolong = rs.getFloat("longitude");
        	  if(geolat != 0 && geolong != 0) {
        		  geoList.add(new GeolocationModel(geolat,geolong));
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
      }
	  return geoList;
  }
}
