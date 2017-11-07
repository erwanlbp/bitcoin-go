package fr.eisti.bitcoin_go.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.bson.BsonArray;
import org.bson.Document;

import java.util.List;

import fr.eisti.bitcoin_go.R;
import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.data.mongodb.Database;
import fr.eisti.bitcoin_go.providers.GoogleMapsProvider;

public class MapShowAllActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    public final static String TAG = "MAPSHOWALL";
    private final static int MY_LOCATION_REQUEST_CODE = 42;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_show_all);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.map = googleMap;

        Database.getInstance().listAll(this)
                .addOnCompleteListener(new OnCompleteListener<List<Document>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Document>> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MapShowAllActivity.this.getApplicationContext(), "Failed, see logs.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, task.getException().toString());
                            return;
                        }

                        Log.i(TAG, "Task successful");
                        for (Document doc : task.getResult()) {
                            Document location = (Document) doc.get("location");
                            if (location == null) {
                                Log.e(TAG, "Location==null : " + doc.toString());
                                continue;
                            }
                            List list = ((List) location.get("coordinates"));
                            LatLng latLng = new LatLng((Double) list.get(0), (Double) list.get(1));
                            map.addMarker(new MarkerOptions().position(latLng));
                        }

                        if (ActivityCompat.checkSelfPermission(MapShowAllActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            map.setMyLocationEnabled(true);
                        } else {
                            ActivityCompat.requestPermissions(MapShowAllActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length >= 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Asshole ...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}