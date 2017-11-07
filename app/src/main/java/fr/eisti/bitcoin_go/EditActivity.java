package fr.eisti.bitcoin_go;

import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.eisti.bitcoin_go.data.Bitcoin;
import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.providers.EditActivityProvider;

public class EditActivity extends AppCompatActivity {

    private EditActivityProvider provider;

    private EditText mName;
    private EditText mValue;
    private EditText mLatitude;
    private EditText mLongitude;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

    this.provider = new EditActivityProvider(this);

        mName = (EditText) findViewById(R.id.edit_name);
        mValue = (EditText) findViewById(R.id.edit_value);
        mLatitude = (EditText) findViewById(R.id.edit_latitude);
        mLongitude = (EditText) findViewById(R.id.edit_longitude);
        btnSave = (Button) findViewById(R.id.edit_save);
    }

    public void save(View view) {
        String name = mName.getText().toString();
        double value = Double.parseDouble(mValue.getText().toString());
        double latitude = Double.parseDouble(mLatitude.getText().toString());
        double longitude = Double.parseDouble(mLongitude.getText().toString());

                this.provider.saveInDB(name, value, latitude, longitude);

    }
}
