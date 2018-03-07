package droppod.models;

import java.net.URL;

public class PodcastModel {
	private String name;
	private URL url;
	private String description;
	private URL thumbnail_url;
	private int id;
	private String uuid;
	
	public PodcastModel() {
		this.name = "";
		this.url = null;
		this.description = "";
		this.thumbnail_url = null;
		this.id = -1;
		this.uuid = null;
	}
	
	public PodcastModel(String name, URL url, String description, URL thumbnail_url, int id, String uuid) {
		super();
		this.name = name;
		this.url = url;
		this.description = description;
		this.thumbnail_url = thumbnail_url;
		this.id = id;
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public URL getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(URL thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
	
	public int getId() {
		return id;
	}	
	
	public void setId(int id) {
		this.id = id;
	}

}