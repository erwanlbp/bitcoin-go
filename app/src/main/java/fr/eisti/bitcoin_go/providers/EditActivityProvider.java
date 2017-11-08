package fr.eisti.bitcoin_go.providers;

import fr.eisti.bitcoin_go.EditActivity;
import fr.eisti.bitcoin_go.data.Bitcoin;
import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.data.elasticSearch.ElasticSearch;

/**
 * Created by ErwanLBP on 06/11/17.
 */

public class EditActivityProvider {

    private EditActivity activity;

    public EditActivityProvider(EditActivity activity) {
        this.activity = activity;
    }

    public void saveInDB(String name, double value, double latitude, double longitude) {
        Bitcoin bitcoin = new Bitcoin(new Location(latitude, longitude), name, value);

        ElasticSearch.insertData(activity.getApplicationContext(), bitcoin);
        activity.finish();
    }
}
