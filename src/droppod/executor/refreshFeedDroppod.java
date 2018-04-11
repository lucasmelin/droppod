package droppod.executor;

import static java.util.concurrent.TimeUnit.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import com.rometools.modules.itunes.FeedInformation;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import droppod.dao.PodcastDao;
import droppod.models.PodcastModel;

public class refreshFeedDroppod {
	 private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
	 
	 public void updatePodcasts() {
	     final Runnable updater = new Runnable() {
	       public void run() { 
	    	  try {
	    		  //This will update the rss feeds for all podcasts
				updateFeed();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	    	   }
	     };
	     final ScheduledFuture<?> beeperHandle =
	    	       scheduler.scheduleAtFixedRate(updater, 0, 10, HOURS);
	    	     scheduler.schedule(new Runnable() {
	    	       public void run() { beeperHandle.cancel(true); }
	    	     }, 356 * 24, HOURS);
	    	   }
	 
	 public void shutdown() {
		 scheduler.shutdown();
	 }
	 
	 public void updateFeed() throws IOException {
		 ArrayList<PodcastModel> podcasts = PodcastDao.getAllPodcasts();
		 
		 for(int i =0;i<podcasts.size();i++) {
		 URL feedSource = podcasts.get(i).getUrl();
	        SyndFeedInput input = new SyndFeedInput();
	        SyndFeed feed = null;
	        try {
				feed = input.build(new XmlReader(feedSource));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (FeedException e) {
				e.printStackTrace();  
			}
	        Module module = feed.getModule("http://www.itunes.com/dtds/podcast-1.0.dtd");
	        FeedInformation feedInfo = (FeedInformation) module;
	        String podcastTitle = feed.getTitle();

	        // If the iTunes format image is present, use that one
	        URL podcastImageLink = feedInfo.getImage();;
	        if (podcastImageLink == null) {
	        	// Otherwise, fallback to the image embedded in the feed
	        	podcastImageLink = new URL(feed.getImage().getUrl());
	        }
	        
	        String podcastDescription = feed.getDescription();
	        /* Basic HTML sanitation*/
	        
	        List<SyndEntry> episodes = feed.getEntries();
	        Date lastPublished = episodes.get(0).getPublishedDate();
	        String podcastUri = feed.getUri();
	        
	        PodcastDao.updatePodcastEpisodes(episodes,podcasts.get(i));
		 }
		
	 }
}