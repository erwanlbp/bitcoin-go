package fr.eisti.bitcoin_go.data;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ErwanLBP on 06/11/17.
 */

public class Bitcoin {

    public static final String LOCATION = "location";
    private Location location;
    public static final String NAME = "nom";
    private String name;
    public final static String VALUE = "value";
    private double value;

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Bitcoin.NAME, name);
            jsonObject.put(Bitcoin.VALUE, value);
            JSONObject loc = new JSONObject();
            JSONObject latlng = new JSONObject();
            latlng.put(Location.LATITUDE, location.getLatitude());
            latlng.put(Location.LONGITUDE, location.getLongitude());
            loc.put("location", latlng);
            jsonObject.put("pin", loc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public Bitcoin(Location location, String name, double value) {
        this.location = location;
        this.name = name;
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ": " + value + " @ " + location;
    }
}
