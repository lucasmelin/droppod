package droppod.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{

  /**
   * 
   */
  private static final long serialVersionUID = -1085241931529423949L;
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    
    if (session.getAttribute("name") != null) {
      session.removeAttribute("name");
      RequestDispatcher rd=req.getRequestDispatcher("index.jsp");  
      rd.include(req,resp);  
    }
    
  }
}
