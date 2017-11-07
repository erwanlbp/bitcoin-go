package fr.eisti.bitcoin_go;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fr.eisti.bitcoin_go.data.Bitcoin;
import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.data.mongodb.Database;
import fr.eisti.bitcoin_go.maps.MapShowAllActivity;
import fr.eisti.bitcoin_go.maps.MapsActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN ACTIVITY";
    public final static String APP_NAME = "BITCOIN_GO";
    public final static String NAME = "NAME";
    public final static String LOCALISATION = "LOCALISATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bitcoin bit = new Bitcoin(new Location(Math.random() * 90, Math.random() * 180), "BTC" + (int) (Math.random() * 20), 10);
        Log.i(TAG, bit.toString());
        Database.getInstance().insertOne(this.getApplicationContext(), bit);

    }

    public void launchMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);

        EditText editTextName = (EditText) findViewById(R.id.name_tv);
        EditText editTextLocalisation = (EditText) findViewById(R.id.localisation_tv);

        intent.putExtra(APP_NAME + NAME, editTextName.getText().toString());
        intent.putExtra(APP_NAME + LOCALISATION, editTextLocalisation.getText().toString());

        startActivity(intent);
    }

    public void logAll(View view) {
        Database.getInstance().listAll(this.getApplicationContext());
        Intent intent = new Intent(this, MapShowAllActivity.class);
        startActivity(intent);
    }

    public void edit(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }
}
