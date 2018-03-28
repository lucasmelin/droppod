package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecommendedServlet extends HttpServlet {

  private static final long serialVersionUID = 6614114375578034226L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    String language = session.getAttribute("language").toString().substring(0, 2);
    response.setContentType("application/json");

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {

      Context envContext = new InitialContext();
      Context initContext = (Context) envContext.lookup("java:/comp/env");
      DataSource ds = (DataSource) initContext.lookup("jdbc/droppod");
      conn = ds.getConnection();

      pst = conn.prepareStatement(
          "SELECT ps1.podcast_id as podcast_id1, ps2.podcast_id as podcast_id2, count(ps1.user_id) AS Overlap, pt1.podcast_name AS podcast_name1, pt2.podcast_name AS podcast_name2\r\n"
              + "FROM droppod.subscriptions ps1 CROSS JOIN\r\n"
              + "     droppod.subscriptions ps2\r\n" + "     ON ps1.user_id = ps2.user_id and\r\n"
              + "        ps1.podcast_id < ps2.podcast_id\r\n"
              + "LEFT JOIN droppod.podcasts_translations AS pt1 ON ps1.podcast_id = pt1.podcast_id\r\n"
              + "AND pt1.language_code = ?\r\n"
              + "LEFT JOIN droppod.podcasts_translations AS pt2 ON ps2.podcast_id = pt2.podcast_id\r\n"
              + "WHERE pt2.language_code = ?\r\n" + "GROUP BY ps1.podcast_id, ps2.podcast_id;");
      
      pst.setString(1, language);
      pst.setString(2, language);

      rs = pst.executeQuery();

      JSONObject chartData = new JSONObject();

      JSONArray nodes = new JSONArray();
      JSONArray links = new JSONArray();

      Set<String> nodeSet = new HashSet<String>();



      while (rs.next()) {
        JSONObject newLink = new JSONObject();

        nodeSet.add(rs.getString("podcast_name1"));
        nodeSet.add(rs.getString("podcast_name2"));


        newLink.put("source", rs.getString("podcast_name1"));
        newLink.put("target", rs.getString("podcast_name2"));
        newLink.put("value", rs.getString("Overlap"));

        links.put(newLink);
      }
      for (String item : nodeSet) {
        JSONObject newNode = new JSONObject();
        newNode.put("name", item);

        nodes.put(newNode);
      }


      chartData.put("nodes", nodes);
      chartData.put("edges", links);

      PrintWriter out = response.getWriter();
      out.print(chartData);
      out.flush();

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
