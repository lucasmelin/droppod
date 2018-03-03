package droppod.listen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.google.cloud.translate.Translation;
import droppod.models.EpisodeModel;
import droppod.models.PodcastModel;

public class TranslateDroppod {

  public static void addEpisodeTranslation(Locale userLocale, List<EpisodeModel> episodes,
      List<Translation> translatedNames, List<Translation> translatedDescriptions) {


    Connection conn = null;
    PreparedStatement pst = null;

    String language = userLocale.getLanguage();

    try {

      Context envContext = new InitialContext();
      Context initContext = (Context) envContext.lookup("java:/comp/env");
      DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
      // DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
      Connection con = ds.getConnection();

      pst = con.prepareStatement(
          "INSERT IGNORE INTO droppod.episodes_translations (episode_id, language_code, episode_name, episode_description) VALUES (?, ?, ?, ?)");


      for (int i = 0; i < episodes.size(); i++) {
        pst.setInt(1, episodes.get(i).getId());
        pst.setString(2, language);
        pst.setString(3, translatedNames.get(i).getTranslatedText());
        pst.setString(4, translatedDescriptions.get(i).getTranslatedText());

        pst.executeUpdate();
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
  }

  public static void addPodcastTranslation(Locale userLocale, PodcastModel podcast, String podcastName, String podcastDescription) {

    Connection conn = null;
    PreparedStatement pst = null;
    String language = userLocale.getLanguage();

    try {

      Context envContext = new InitialContext();
      Context initContext = (Context) envContext.lookup("java:/comp/env");
      DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
      // DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/droppod");
      Connection con = ds.getConnection();

      pst = con.prepareStatement(
          "INSERT IGNORE INTO droppod.podcasts_translations (podcast_id, language_code, podcast_name, podcast_description) VALUES (?, ?, ?, ?)");

      pst.setInt(1, podcast.getId());
      pst.setString(2, language);
      pst.setString(3, podcastName);
      pst.setString(4, podcastDescription);

      pst.executeUpdate();

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
  }
}
