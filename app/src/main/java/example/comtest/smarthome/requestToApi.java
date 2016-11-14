package example.comtest.smarthome;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    String response;
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

        return response;
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

        return response;
    }
}
