package droppod.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeolocateServlet extends HttpServlet{

  /**
   * 
   */
  private static final long serialVersionUID = -174759430770425383L;


  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    Double latitude = Double.valueOf(request.getParameter("lat").toString());
    Double longitude = Double.valueOf(request.getParameter("long").toString());
    
    System.out.println(latitude);
    System.out.println(longitude);
  }

}
