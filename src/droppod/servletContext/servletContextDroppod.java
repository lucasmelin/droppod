package droppod.servletContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import droppod.executor.refreshFeedDroppod;
@WebListener
public class servletContextDroppod implements ServletContextListener{
	private static refreshFeedDroppod refresh = null;
	//This function will run automatically on the web application starting up
	@Override
	public void contextInitialized(ServletContextEvent event) {
		refresh = new refreshFeedDroppod();
		refresh.updatePodcasts();
	}
	//This function will run automatically on the web application closing down
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		refresh.shutdown();
	}
}
