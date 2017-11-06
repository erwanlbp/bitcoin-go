package fr.eisti.bitcoin_go.data;


/**
 * Created by ErwanLBP on 06/11/17.
 */

public class Bitcoin {

    public static final String LOCATION = "location";
    private Location location;
    public static final String NAME = "name";
    private String name;
    public final static String VALUE = "value";
    private double value;


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
