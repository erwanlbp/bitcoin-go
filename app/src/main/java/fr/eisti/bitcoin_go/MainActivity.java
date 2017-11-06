package fr.eisti.bitcoin_go;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import fr.eisti.bitcoin_go.maps.MapsActivity;

public class MainActivity extends AppCompatActivity {

    public final static String APP_NAME = "BITCOIN_GO";
    public final static String NAME = "NAME";
    public final static String LOCALISATION = "LOCALISATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);

        EditText editTextName = (EditText) findViewById(R.id.name_tv);
        EditText editTextLocalisation = (EditText) findViewById(R.id.localisation_tv);

        intent.putExtra(APP_NAME + NAME, editTextName.getText().toString());
        intent.putExtra(APP_NAME + LOCALISATION, editTextLocalisation.getText().toString());

        startActivity(intent);
    }
}
