package droppod.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/admin.jsp")
public class AdminFilter implements Filter{

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("accessLevel") == null || (Integer) session.getAttribute("accessLevel") == 0) {
        response.sendRedirect(request.getContextPath() + "/welcome.jsp");
    } else {
        chain.doFilter(request, response);
    }
    
  }

@Override
public void init(FilterConfig filterConfig) throws ServletException {
    // TODO Auto-generated method stub
    
}

@Override
public void destroy() {
    // TODO Auto-generated method stub
    
}

}
