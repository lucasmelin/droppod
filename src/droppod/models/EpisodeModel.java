package droppod.models;

import java.net.URL;

public class EpisodeModel {
  private String name;
  private URL url;
  private String description;
  private int id;

  public EpisodeModel() {
    this.name = "";
    this.url = null;
    this.description = "";
    this.id = -1;
  }

  public EpisodeModel(String name, URL url, String description, int id) {
    super();
    this.name = name;
    this.url = url;
    this.description = description;
    this.id = id;
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

  public void setUrl(URL url) {
    this.url = url;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
