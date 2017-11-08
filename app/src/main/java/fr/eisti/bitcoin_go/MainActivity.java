package fr.eisti.bitcoin_go;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import fr.eisti.bitcoin_go.data.Bitcoin;
import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.data.mongodb.Database;
import fr.eisti.bitcoin_go.maps.MapsActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN ACTIVITY";
    public final static String APP_NAME = "BITCOIN_GO";
    public final static String NAME = "NAME";
    public final static String LOCALISATION = "LOCALISATION";
    public final static String DISTANCE = "DISTANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for (int i = 0; i < 3; i++) {
            Bitcoin bit = new Bitcoin(new Location(Math.random() * 180 - 90, -180 + Math.random() * 360), "BTC" + (int) (Math.random() * 50), 10);
            Log.i(TAG, bit.toString());
            Database.getInstance().insertOne(this.getApplicationContext(), bit);
        }

    }

    public void launchMap(View view) {
        int distance = ((SeekBar) findViewById(R.id.main_seekbar)).getProgress();

        Intent intent = new Intent(this, MapsActivity.class);

        EditText editTextName = (EditText) findViewById(R.id.name_tv);
        EditText editTextLocalisation = (EditText) findViewById(R.id.localisation_tv);

        intent.putExtra(APP_NAME + NAME, editTextName.getText().toString());
        intent.putExtra(APP_NAME + LOCALISATION, editTextLocalisation.getText().toString());
        intent.putExtra(APP_NAME + DISTANCE, distance);

        startActivity(intent);
    }

    public void edit(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }
}
