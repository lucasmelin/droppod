package droppod.servlets;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetLanguageServlet extends HttpServlet {

  private static final long serialVersionUID = 4595960441202509050L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    String language = request.getParameter("language");

    HttpSession session = request.getSession(false);

    if (session != null)
      session.setAttribute("language", language);

    String previousURL = request.getHeader("referer");
    try {
      response.sendRedirect(previousURL);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
