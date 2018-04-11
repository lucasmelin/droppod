package droppod.location;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class Geolocate {
    
  
  public GeocodingResult geolocateAddress(String address) {
    GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyC7NysljEQDwB01a-ASfSY7hveHIjht1ak").build();
    GeocodingResult[] results = null;
    try {
      results = GeocodingApi.geocode(context, address).await();
    } catch (ApiException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return results[0];
    
  }
  
  
  
  
  public void latLongtoCity(Double latitude, Double longitude) {
    GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyC7NysljEQDwB01a-ASfSY7hveHIjht1ak").build();
    GeocodingResult[] results = null;
    try {
      LatLng latlong = new LatLng(latitude, longitude);
      results = GeocodingApi.reverseGeocode(context, latlong).await();
    } catch (ApiException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    System.out.println(gson.toJson(results[0].addressComponents));
    
  }
}
