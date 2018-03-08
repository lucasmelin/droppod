package droppod.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetLanguageServlet extends HttpServlet{

	private static final long serialVersionUID = 4595960441202509050L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String language = request.getParameter("language");
		request.setAttribute("locale", language);
		
		String previousURL = request.getHeader("referer");
		try {
			response.sendRedirect(previousURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
