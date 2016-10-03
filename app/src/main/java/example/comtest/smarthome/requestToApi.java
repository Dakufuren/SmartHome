package example.comtest.smarthome;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bumblebee on 2016-10-03.
 */
public class requestToApi {

    String response;
    private RequestQueue requestQueue;
    private StringRequest request;
    private static String URL = "http://smarthomeinterface.azurewebsites.net/home/3";
    Context context;

    public requestToApi(Context context) {
        this.context = context;

    }

    public String postToServer(final String commandId, final String sensorId, final String userId){
        requestQueue = Volley.newRequestQueue(context);

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ack will be shown here
                System.out.println("i was here");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("hej");
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //hashmap represents the input that is going to be sent
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("commandId:", commandId);
                hashMap.put("sensorId:", sensorId);
                hashMap.put("userId:", userId);
                System.out.println(hashMap);

                return hashMap;
            }

        };
        System.out.println("sending request");
        requestQueue.add(request);

        return response;
    }
}
