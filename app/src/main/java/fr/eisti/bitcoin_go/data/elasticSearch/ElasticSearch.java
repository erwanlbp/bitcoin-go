package fr.eisti.bitcoin_go.data.elasticSearch;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import fr.eisti.bitcoin_go.data.Bitcoin;

public class ElasticSearch {

    public static final String TAG = "##### ELASTIC SEARCH";

    public static void insertData(Context context, Bitcoin bitcoin) {
        Log.i(TAG, "Insert : " + bitcoin.getName());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, SearchSingleton.URL + bitcoin.getName(), bitcoin.toJSONObject(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                });

        // Access the RequestQueue through your singleton class.
        SearchSingleton.getInstance(context).addToRequestQueue(jsObjRequest);

    }

}
