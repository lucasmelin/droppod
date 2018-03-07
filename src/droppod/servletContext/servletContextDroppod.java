package droppod.servletContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import droppod.executor.refreshFeedDroppod;
@WebListener
public class servletContextDroppod implements ServletContextListener{
	private static refreshFeedDroppod refresh = null;
	@Override
	public void contextInitialized(ServletContextEvent event) {
		refresh = new refreshFeedDroppod();
		refresh.beepForAnHour();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		refresh.shutdown();
	}
}
