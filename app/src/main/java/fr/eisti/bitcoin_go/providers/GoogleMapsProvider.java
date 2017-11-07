package fr.eisti.bitcoin_go.providers;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.bson.BsonArray;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eisti.bitcoin_go.data.Bitcoin;
import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.data.mongodb.Database;
import fr.eisti.bitcoin_go.maps.MapsActivity;

public class GoogleMapsProvider {

    public static final String TAG = "### GOOGLEMAPSPROVIDER";
    private MapsActivity mapsActivity;

    public GoogleMapsProvider(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    public void printResult(String name, final String localisation, final GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(mapsActivity);

        Task<List<Document>> task = null;

        if (!name.isEmpty()) {
            task = Database.getInstance().find(mapsActivity.getApplicationContext(), name);
        } else if (!localisation.isEmpty()) {
            try {
                List<Address> results = geocoder.getFromLocationName(localisation, 1);
                if (results.size() != 0) {
                    LatLng originalLatLng = new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude());
                    Location location = new Location(originalLatLng.latitude, originalLatLng.longitude);

                    task = Database.getInstance().find(mapsActivity.getApplicationContext(), location);

                } else {
                    Toast.makeText(mapsActivity, "Aucun résultat trouvé", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Aucun résultat trouvé");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mapsActivity, "Localisation et nom null", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Localisation et nom null");
            return;
        }

        if (task == null) {
            Toast.makeText(mapsActivity.getApplicationContext(), "Task is null", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Task is null");
            return;
        }

        task.addOnCompleteListener(new OnCompleteListener<List<Document>>() {
            @Override
            public void onComplete(@NonNull Task<List<Document>> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Task successful");
                    Log.i(TAG, task.getResult().toString());
                    LatLng latLng = new LatLng(0, 0);
                    for (Document doc : task.getResult()) {
                        Document location = (Document) doc.get("location");
                        List list = ((List) location.get("coordinates"));
                        latLng = new LatLng((Double) list.get(0), (Double) list.get(1));
                        googleMap.addMarker(new MarkerOptions().position(latLng));
                    }

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

                    return;
                }
                Log.e(TAG, task.getException().toString());
            }
        });

    }

}
