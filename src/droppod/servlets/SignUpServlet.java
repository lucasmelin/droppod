package droppod.servlets;

import droppod.bcrypt.BCrypt;
import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import droppod.mail.MailDroppod;
import droppod.dao.UserDao;

public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uuid = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String hashedpassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

		HttpSession session = request.getSession(false);

		if (session != null)
			session.setAttribute("name", name);

		if (password.equals(repassword)) {
			session.setAttribute("passwordMismatch", "false");
			if (UserDao.add(name, hashedpassword, email, city, country)) {
				session.setAttribute("databaseInsertFailure", "false");
				//uuid = UserDao.getUuid(name);
				//if (uuid != null) {
				//	try {
				//		MailDroppod.sendEmail(email, uuid);
				//	} catch (MessagingException ex) {
				//		ex.printStackTrace();
				//	}
				//}
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			} else {
				session.setAttribute("databaseInsertFailure", "true");
				response.sendRedirect(request.getHeader("referer"));
			}
		} else {
			session.setAttribute("passwordMismatch", "true");
			response.sendRedirect(request.getHeader("referer"));
		}

		out.close();
	}
}
