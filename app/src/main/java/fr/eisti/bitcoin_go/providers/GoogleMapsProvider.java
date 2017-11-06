package fr.eisti.bitcoin_go.providers;

import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.maps.MapsActivity;

public class GoogleMapsProvider {

    private MapsActivity mapsActivity;

    public GoogleMapsProvider(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    public void printResult(String name, String localisation, GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(mapsActivity);

        if (name != null) {

            //TODO
            Location location = ...;

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));

        } else if (localisation != null) {
            try {
                List<Address> results = geocoder.getFromLocationName(localisation, 1);
                if (results.size() != 0) {
                    LatLng originalLatLng = new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude());

                    //TODO
                    List<Location> locationList = ...;

                    for (Location address : locationList) {
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng));
                    }
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(originalLatLng));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
                } else
                    Toast.makeText(mapsActivity, "Aucun résultat trouvé", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else Toast.makeText(mapsActivity, "Localisation et nom null", Toast.LENGTH_SHORT).show();
    }

}
