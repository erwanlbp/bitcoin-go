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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eisti.bitcoin_go.data.Bitcoin;
import fr.eisti.bitcoin_go.data.Location;
import fr.eisti.bitcoin_go.providers.GoogleMapsProvider;

public class ElasticSearch {

    public final static String URL = "https://ad6325711a0b6c0ac5db85b1293696d7.eu-west-1.aws.found.io:9243/bitcoin/data/";
    public final static String source = "_source";
    public static final String TAG = "##### ELASTIC SEARCH";

    private static RequestQueue queue;

    public static void insertData(Context context, final Bitcoin bitcoin) {

        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, bitcoin.toJSONObject(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Inserted " + bitcoin.getName());
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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

    public static void find(Context context, final String name, final GoogleMapsProvider mapProvider) {

        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL + "_search", formNameQuery(name), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONArray arr = response.getJSONObject("hits").getJSONArray("hits");
                            Log.i(TAG, "Found " + arr.length() + " pins");
                            List<Location> locations = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                locations.add(Location.parseFromOne(arr.getJSONObject(i)));
                            }
                            mapProvider.addOnMap(locations);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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


    public static void find(Context context, final Location baseLocation, int distance, final GoogleMapsProvider mapProvider) {

        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL + "_search", formGeoDistanceQuery(baseLocation, distance), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONArray arr = response.getJSONObject("hits").getJSONArray("hits");
                            Log.i(TAG, "Found " + arr.length() + " pins");
                            List<Location> locations = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                locations.add(Location.parseFromOne(arr.getJSONObject(i)));
                            }
                            mapProvider.addOnMap(locations);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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

    public static void find(Context context, final GoogleMapsProvider mapProvider) {

        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL + "_search", formAllQuery(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arr = response.getJSONObject("hits").getJSONArray("hits");
                            Log.i(TAG, "Found " + arr.length() + " pins");
                            List<Location> locations = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                locations.add(Location.parseFromOne(arr.getJSONObject(i)));
                            }
                            mapProvider.addOnMap(locations);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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


    private static JSONObject formGeoDistanceQuery(Location location, int distance) {
        JSONObject result;
        try {

            result = new JSONObject("{\n" +
                    "        \"from\" : 0, \"size\" : 9999, \"query\": {\n" +
                    "            \"bool\" : {\n" +
                    "                \"must\" : {\n" +
                    "                    \"match_all\" : {}\n" +
                    "                },\n" +
                    "                \"filter\" : {\n" +
                    "                    \"geo_distance\" : {\n" +
                    "                        \"distance\" : \"" + distance + "km\",\n" +
                    "                        \"pin.location\" : {\n" +
                    "                            \"" + Location.LATITUDE + "\" : " + location.getLatitude() + ",\n" +
                    "                            \"" + Location.LONGITUDE + "\" : " + location.getLongitude() + "\n" +
                    "                        }\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }");
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }

        return result;
    }

    private static JSONObject formNameQuery(String name) {
        JSONObject result;
        try {
            result = new JSONObject("{\n" +
                    "     \"from\" : 0, \"size\" : 9999, \"query\": {\n" +
                    "        \"constant_score\": {\n" +
                    "          \"filter\": {\n" +
                    "            \"term\": { \"" + Bitcoin.NAME + "\":   \"" + name + "\" }\n" +
                    "          }\n" +
                    "        }\n" +
                    "      }\n" +
                    "    }");
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
        Log.i(TAG, result.toString());
        return result;
    }

    private static JSONObject formAllQuery() {
        JSONObject result;
        try {
            result = new JSONObject("{\n" +
                    "     \"from\" : 0, \"size\" : 9999, \"query\": {\n" +
                    "        \"match_all\": {}\n" +
                    "      }\n" +
                    "    }");
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
        return result;
    }
}
