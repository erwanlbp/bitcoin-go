package fr.eisti.bitcoin_go.data;

/**
 * Created by ErwanLBP on 06/11/17.
 */

public class Location {

    public static final String LATITUDE = "latitude";
    private double latitude;
    public static final String LONGITUDE = "longitude";
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "(" + latitude + " ; " + longitude + ")";
    }
}
