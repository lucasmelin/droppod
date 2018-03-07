package droppod.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class GeolocateServlet extends HttpServlet{

  /**
   * 
   */
  private static final long serialVersionUID = -174759430770425383L;


  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");


    StringBuffer jb = new StringBuffer();
    String line = null;
    JSONObject jsonObject = null;
    try {
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null)
        jb.append(line);
    } catch (Exception e) { /*report an error*/ }

    try {
      jsonObject =  HTTP.toJSONObject(jb.toString());
    } catch (JSONException e) {
      // crash and burn
      throw new IOException("Error parsing JSON request string");
    }
    Double latitude = jsonObject.getDouble("lat");
    Double longitude = jsonObject.getDouble("long");
  }

}
