package fr.eisti.bitcoin_go.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

import fr.eisti.bitcoin_go.data.elasticSearch.ElasticSearch;

/**
 * Created by ErwanLBP on 06/11/17.
 */

public class Location {

    public static final String LATITUDE = "lat";
    public static final double DEFAULT_LATITUDE = 4000;
    private double latitude;
    public static final String LONGITUDE = "lon";
    public static final double DEFAULT_LONGITUDE = 4000;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
        this(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    }

    public static Location parseFromOne(JSONObject obj) throws JSONException {
        JSONObject latlng = obj.getJSONObject(ElasticSearch.source).getJSONObject("pin").getJSONObject("location");
        return new Location(latlng.getDouble(LATITUDE), latlng.getDouble(LONGITUDE));
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
