package droppod.models;

public class GeolocationModel {
	private float latitude;
	private float longitude;
	
	public GeolocationModel() {
		this.latitude = 0;
		this.longitude = 0;
	}
	
	public GeolocationModel(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public float getlatitude() {
		return this.latitude;
	}
	public float getlongitude() {
		return this.longitude;
	}
}
