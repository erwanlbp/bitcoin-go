package fr.eisti.bitcoin_go.providers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.data.elasticSearch.ElasticSearch;
import fr.eisti.bitcoin_go.maps.MapsActivity;

public class GoogleMapsProvider implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final String TAG = "### GOOGLEMAPSPROVIDER";
    private MapsActivity mapsActivity;
    private final static int MY_LOCATION_REQUEST_CODE = 42;

    private GoogleMap map;

    public GoogleMapsProvider(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    public void printResult(String name, final String localisation, final GoogleMap googleMap) {
        map = googleMap;

        Geocoder geocoder = new Geocoder(mapsActivity);

        if (!name.isEmpty()) {
            ElasticSearch.find(mapsActivity.getApplicationContext(), name, this);

        } else if (!localisation.isEmpty()) {
            try {
                List<Address> results = geocoder.getFromLocationName(localisation, 1);
                if (results.size() != 0) {
                    LatLng originalLatLng = new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude());
                    Location location = new Location(originalLatLng.latitude, originalLatLng.longitude);

                    ElasticSearch.find(mapsActivity.getApplicationContext(), location, 1000, this);
                } else {
                    Toast.makeText(mapsActivity, "Aucun résultat trouvé", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Aucun résultat trouvé");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            ElasticSearch.find(mapsActivity.getApplicationContext(), this);
        }

        if (ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(mapsActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }

    }

    public void addOnMap(List<Location> locations) {
        if (locations == null || locations.isEmpty()) {
            Toast.makeText(mapsActivity.getApplicationContext(), "Nothing to add", Toast.LENGTH_SHORT).show();
            return;
        }

        LatLng latLng = new LatLng(0, 0);
        for (Location loc : locations) {
            latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            map.addMarker(new MarkerOptions().position(latLng));
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length >= 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                Toast.makeText(mapsActivity, "Asshole ...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
