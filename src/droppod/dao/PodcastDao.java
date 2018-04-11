package droppod.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.rometools.modules.itunes.FeedInformation;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import droppod.models.PodcastModel;

public class PodcastDao {
  public static ArrayList<PodcastModel> getAllPodcasts() {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ArrayList<PodcastModel> podList = new ArrayList<PodcastModel>();
    try {
      Context envContext = new InitialContext();
      Context initContext = (Context) envContext.lookup("java:/comp/env");
      DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
      conn = ds.getConnection();
      pst = conn.prepareStatement("select * from droppod.podcasts");
      rs = pst.executeQuery();
      while (rs.next()) {
        int podId = rs.getInt("id");
        String urlString = rs.getString("url");
        URL url = new URL(urlString);
        PodcastModel podcast = new PodcastModel(podId, url);
        podList.add(podcast);
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
    if (!podList.isEmpty()) {
      return podList;
    } else {
      return podList;
    }
  }

  public static boolean updatePodcastEpisodes(List<SyndEntry> episodes, PodcastModel podcast) {
    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement pstEpisodeInfo = null;
    PreparedStatement pstEpisodeTran = null;
    ResultSet rs = null;
    ArrayList<Integer> idList = new ArrayList<Integer>();
    Timestamp lastAdded = null;
    try {
      Context envContext = new InitialContext();
      Context initContext = (Context) envContext.lookup("java:/comp/env");
      DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
      conn = ds.getConnection();
      // Retrieve all episodes related to the podcast
      pst = conn.prepareStatement(
          "select * from droppod.episodes where podcast_id = ? order by release_date desc");
      int podId = podcast.getId();
      pst.setInt(1, podId);
      rs = pst.executeQuery();

      if (rs.next()) {
        lastAdded = rs.getTimestamp("release_date");
        if (lastAdded.compareTo(episodes.get(0).getPublishedDate()) != 0) {
          int a = 0;
          java.util.Date episodeDate = episodes.get(0).getPublishedDate();
          // compare the release date to find the last podcast in the rss feed that was added
          while ((a < episodes.size()) && (lastAdded.compareTo(episodeDate) != 0)) {

            a++;
            if (a < episodes.size()) {
              episodeDate = episodes.get(a).getPublishedDate();
            }
          }
          if (a < episodes.size() && a != 0) {
            // create a sublist so we can add only the episodes that arent in the database
            episodes = episodes.subList(0, a);
          }

          pstEpisodeInfo = conn.prepareStatement(
              "INSERT IGNORE INTO droppod.episodes (url, podcast_id, release_date)"
                  + "VALUES  (?, ?, ?)");
          pstEpisodeTran = conn.prepareStatement(
              "INSERT IGNORE INTO droppod.episodes_translations (episode_id, language_code, episode_name, episode_description) VALUES (LAST_INSERT_ID(), ?, ?, ?)");
          for (int j = 0; j < episodes.size(); j++) {
            SyndEntry ep = episodes.get(j);
            pstEpisodeInfo.setString(1, ep.getEnclosures().get(0).getUrl());
            pstEpisodeInfo.setInt(2, podId);
            pstEpisodeInfo.setTimestamp(3, new java.sql.Timestamp(ep.getPublishedDate().getTime()));

            pstEpisodeInfo.executeUpdate();

            pstEpisodeTran.setString(1, "en");
            pstEpisodeTran.setString(2, ep.getTitle());
            pstEpisodeTran.setString(3, ep.getDescription().getValue());

            pstEpisodeTran.executeUpdate();
          }
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
      if (pstEpisodeInfo != null) {
        try {
          pstEpisodeInfo.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (pstEpisodeTran != null) {
        try {
          pstEpisodeTran.close();
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

    return true;
  }


}
