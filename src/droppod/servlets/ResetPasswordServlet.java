package droppod.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import droppod.dao.UserDao;

public class ResetPasswordServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String uuid = request.getParameter("uuid");
    String password = request.getParameter("password");
    String rePassword = request.getParameter("repassword");
    if (password.equals(rePassword)) {
      UserDao.changePassword(uuid, password);
      RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
      rd.forward(request, response);
    } else {
      RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
      rd.include(request, response);
    }
  }
}
