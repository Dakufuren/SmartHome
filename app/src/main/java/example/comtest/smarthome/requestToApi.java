package example.comtest.smarthome;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.category;

/**
 * Created by bumblebee on 2016-10-03.
 */
public class requestToApi {

    String responsePost;
    private RequestQueue requestQueue;
    private StringRequest request;
    private JsonObjectRequest request2;
    private JsonObjectRequest getRequest;
    private static String URL = "http://smarthomeinterface.azurewebsites.net/home/3";
    Context context;

    public requestToApi(Context context) {
        this.context = context;

    }

    public String postToServer(final String commandId, final String sensorId, final String userId){
        requestQueue = Volley.newRequestQueue(context);

        JSONObject infoSent = new JSONObject();
        try {
            infoSent.put("commandId", commandId);
            infoSent.put("sensorId", sensorId);
            infoSent.put("userId", userId);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        request2 = new JsonObjectRequest(
                Request.Method.POST,URL, infoSent,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response here");
                        try {
                            System.out.println(response.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " );
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        System.out.println("sending request");
        requestQueue.add(request2);

        return responsePost;
    }

    public String getFromServer(final String commandId, final String sensorId, final String userId){
        requestQueue = Volley.newRequestQueue(context);

        java.net.URL url = null;
        String query = "?commandId=" + commandId +
                "&sensorId=" + sensorId +
                "&userId=" + userId +
                "&unitChannel=" + "hkr_channel_unit";
        try {
            url = new URL("http", "smarthomeinterface.azurewebsites.net", "home/1" + query);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println("Error in requests API1");
        }

        getRequest = new JsonObjectRequest(
                Request.Method.GET, url.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response here");
                        try {
                            System.out.println(response.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("Error in requests API2");
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        System.out.println("sending request");
        requestQueue.add(getRequest);

        return responsePost;
    }

    public String checkUser(final String username, final String password, final LogInActivity logInAct){
        requestQueue = Volley.newRequestQueue(context);
        JSONObject infoSent = new JSONObject();
        try {
            infoSent.put("username", username);
            infoSent.put("password", password);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        request2 = new JsonObjectRequest(
                Request.Method.POST,"http://smarthomeinterface.azurewebsites.net/user/login", infoSent,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response here");

                        try {
                            if(response.has("id")){
                                responsePost = response.getString("id");
                                logInAct.checkCredantial(responsePost);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " );
                responsePost = "Wrong credentials";
                logInAct.checkCredantial(responsePost);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        System.out.println("sending request");
        requestQueue.add(request2);
        return responsePost;
    }

    public String getAllHousesFromUser(){
        requestQueue = Volley.newRequestQueue(context);
        System.out.println("TESTING:   getFromServerTEST1");
        java.net.URL url = null;

        try {
            url = new URL("http", "smarthomeinterface.azurewebsites.net", "/getHomeServers/" + DataStorage.getInstance().getUserId());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println("Error in requests API1");
        }

        JsonArrayRequest req = new JsonArrayRequest(url.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("SUCCESSFULL RESPONSE FROM HOMESERVER");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Get the json object and display it
                                JSONObject jo = response.getJSONObject(i);
                                System.out.println("JSON OBJECTET I ARRAYN" + jo.toString());
                                DataStorage.getInstance().addHomeServerToList(jo.get("Homeserver_id").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        System.out.println("sending request");
        requestQueue.add(req);

        return responsePost;
    }

    public String getAllRoomsFromHouse(String homeserverId){
        requestQueue = Volley.newRequestQueue(context);
        System.out.println("TESTING:   getFromServerTEST1");
        java.net.URL url = null;

        try {
            url = new URL("http", "smarthomeinterface.azurewebsites.net", "/getRooms/" + homeserverId);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println("Error in requests API1");
        }

        JsonArrayRequest req = new JsonArrayRequest(url.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("SUCCESSFULL RESPONSE FROM HOMESERVER");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Get the json object and display it
                                JSONObject jo = response.getJSONObject(i);
                                System.out.println("JSON OBJECTET I ARRAYN" + jo.toString());
                                //DataStorage.getInstance().addHouseToList(jo.get("Homeserver_id").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        System.out.println("sending request");
        requestQueue.add(req);

        return responsePost;
    }

    public String getAllDevicesFromRoom(String roomId){
        requestQueue = Volley.newRequestQueue(context);
        System.out.println("TESTING:   getFromServerTEST1");
        java.net.URL url = null;

        try {
            url = new URL("http", "smarthomeinterface.azurewebsites.net", "/getDevices/" + roomId);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println("Error in requests API1");
        }

        JsonArrayRequest req = new JsonArrayRequest(url.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("SUCCESSFULL RESPONSE FROM HOMESERVER");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Get the json object and display it
                                JSONObject jo = response.getJSONObject(i);
                                System.out.println("JSON OBJECTET I ARRAYN" + jo.toString());
                                //DataStorage.getInstance().addHouseToList(jo.get("Homeserver_id").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        System.out.println("sending request");
        requestQueue.add(req);

        return responsePost;
    }

    public String getAllSensorsFromDevice(String deviceId){
        requestQueue = Volley.newRequestQueue(context);
        System.out.println("TESTING:   getFromServerTEST1");
        java.net.URL url = null;

        try {
            url = new URL("http", "smarthomeinterface.azurewebsites.net", "/getDevices/" + deviceId);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println("Error in requests API1");
        }

        JsonArrayRequest req = new JsonArrayRequest(url.toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("SUCCESSFULL RESPONSE FROM HOMESERVER");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Get the json object and display it
                                JSONObject jo = response.getJSONObject(i);
                                System.out.println("JSON OBJECTET I ARRAYN" + jo.toString());
                                //DataStorage.getInstance().addHouseToList(jo.get("Homeserver_id").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        System.out.println("sending request");
        requestQueue.add(req);

        return responsePost;
    }
}
