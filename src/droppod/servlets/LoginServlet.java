package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import droppod.login.LoginDroppod;

public class LoginServlet extends HttpServlet {

  private static final long serialVersionUID = 213123L;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    String n = request.getParameter("username");
    String p = request.getParameter("password");

    String[] podcastIDs = null;

    HttpSession session = request.getSession(false);

    if (session != null)
      session.setAttribute("name", n);

    if (LoginDroppod.validate(n, p)) {
      session.setAttribute("failedLogin", "false");
      podcastIDs = LoginDroppod.addUuidSession(n);
      session.setAttribute("podcastIDs", podcastIDs);
      session.setAttribute("accessLevel", LoginDroppod.verifyAccess(n));
      RequestDispatcher rd = request.getRequestDispatcher("/welcome.jsp");
      rd.forward(request, response);
    } else {
      session.setAttribute("failedLogin", "true");
      response.sendRedirect(request.getHeader("referer"));
    }

  }
}
