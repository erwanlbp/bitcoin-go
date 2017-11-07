package fr.eisti.bitcoin_go.providers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import fr.eisti.bitcoin_go.MainActivity;
import fr.eisti.bitcoin_go.elasticSearch.SearchSingleton;

public class ElasticSearchProvider {

    public static final String TAG = "SEARCH PROVIDER";

    private MainActivity mainActivity;

    public ElasticSearchProvider(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void insertData() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, SearchSingleton.URL, createJSONObject(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Access the RequestQueue through your singleton class.
        SearchSingleton.getInstance(mainActivity).addToRequestQueue(jsObjRequest);

    }

    private JSONObject createJSONObject() {

    }

}
