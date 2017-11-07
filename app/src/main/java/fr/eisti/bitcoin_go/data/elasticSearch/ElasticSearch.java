package fr.eisti.bitcoin_go.data.elasticSearch;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.eisti.bitcoin_go.data.Bitcoin;

public class ElasticSearch {

    public final static String URL = "https://ad6325711a0b6c0ac5db85b1293696d7.eu-west-1.aws.found.io:9243/bitcoin/data/";

    public static final String TAG = "##### ELASTIC SEARCH";

    private static RequestQueue queue;

    public static void insertData(Context context, Bitcoin bitcoin) {
        Log.i(TAG, "Insert : " + bitcoin.getName());

        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL + "BTC24", bitcoin.toJSONObject(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }

                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                String creds = String.format("%s:%s", "elastic", "yCHHvOlXb64kc4HXo6M5PTzI");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }

        };


        // Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);

    }

}
