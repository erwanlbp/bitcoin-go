package fr.eisti.bitcoin_go.maps;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import fr.eisti.bitcoin_go.R;
import fr.eisti.bitcoin_go.providers.GoogleMapsProvider;

import static fr.eisti.bitcoin_go.MainActivity.APP_NAME;
import static fr.eisti.bitcoin_go.MainActivity.LOCALISATION;
import static fr.eisti.bitcoin_go.MainActivity.NAME;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMapsProvider googleMapsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleMapsProvider = new GoogleMapsProvider(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent = getIntent();
        String cityName = intent.getStringExtra(APP_NAME + NAME);
        String cityLocalication = intent.getStringExtra(APP_NAME + LOCALISATION);

        googleMapsProvider.printResult(cityName, cityLocalication, googleMap);
    }
}
