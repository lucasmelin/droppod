package droppod.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("*.jsp")
public class NoCacheFilter implements Filter{

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) resp;
    
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies

    chain.doFilter(req, resp);
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
