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

    boolean[] checkIfDone = {false, false, false, false};
    String responsePost;
    private RequestQueue requestQueue;
    private StringRequest request;
    private JsonObjectRequest request2;
    private JsonObjectRequest getRequest;
    private static String URL = "http://smarthomeinterface.azurewebsites.net/home/";
    Context context;

    public requestToApi(Context context) {
        this.context = context;

    }

    public String postToServer(final String commandId, final String sensorId, final String userId, final String homeId){
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
                Request.Method.POST,URL + "homeId", infoSent,
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

    public String getSensorInformation(final String commandId, final String sensorId, final String userId, final String homeId){
        requestQueue = Volley.newRequestQueue(context);

        java.net.URL url = null;
        String query = "?commandId=" + commandId +
                "&sensorId=" + sensorId +
                "&userId=" + userId +
                "&unitChannel=" + "hkr_channel_unit";
        try {
            url = new URL("http", "smarthomeinterface.azurewebsites.net", "home/" + homeId + query);
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
                                DataStorage.getInstance().addHomeServerToList(jo.get("Server_name").toString(), jo.get("Homeserver_id").toString(), jo.get("User_id").toString());
                                getAllRoomsFromHouse(jo.getString("Homeserver_id").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                        //System.out.println("wwwooowwwooeeoeoeoe");
                        checkIfDone[0] = true;
                        //getStateOfAllSensors(DataStorage.getInstance().getChosenHouseId());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("Error in request" + error.toString());
            }
        });

        System.out.println("sending request");
        requestQueue.add(req);

        return responsePost;
    }

    public String getAllRoomsFromHouse(final String homeserverId){
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
                                DataStorage.getInstance().addRoomToList(jo.get("Room_name").toString(), jo.get("Room_id").toString(), homeserverId);
                                getAllDevicesFromRoom(jo.getString("Room_id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                        if(checkIfDone[0] == true) {
                            checkIfDone[1] = true;
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("Error in request");
            }
        });

        System.out.println("sending request");
        requestQueue.add(req);

        return responsePost;
    }

    public String getAllDevicesFromRoom(final String roomId){
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
                                DataStorage.getInstance().addDeviceToList(jo.get("Device_name").toString(), jo.get("Device_id").toString(), roomId);
                                getAllSensorsFromDevice(jo.getString("Device_id"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                        if(checkIfDone[1] == true) {
                            checkIfDone[2] = true;
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

    public String getAllSensorsFromDevice(final String deviceId){
        requestQueue = Volley.newRequestQueue(context);
        System.out.println("TESTING:   getFromServerTEST1");
        java.net.URL url = null;

        try {
            url = new URL("http", "smarthomeinterface.azurewebsites.net", "/getSensors/" + deviceId);
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
                                System.out.println("JSON OBJECTET I ARRAYN222" + jo.toString());
                                DataStorage.getInstance().addSensorToList(jo.get("Sensor_name").toString(), jo.getString("Sensor_id").toString(), jo.getString("Sensor_type").toString(), deviceId, "0");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error in requests API2");
                            }

                        }
                        if(checkIfDone[2] == true) {
                            checkIfDone[3] = true;
                        }
                        if(checkIfDone[3] == true){
                            System.out.println("maaaaaaaaaaaaaajjjjjssss");
                            getStateOfAllSensors(DataStorage.getInstance().getChosenHouseId());
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

    public void getStateOfAllSensors(String homeServerId){
        for(RoomInfo room : DataStorage.getInstance().getRoomList()){
            if(room.getHomeServerId().equalsIgnoreCase(homeServerId)){
                for(DeviceInfo device : DataStorage.getInstance().getDeviceList()){
                    if(room.getId().equalsIgnoreCase(device.getRoomId())){
                        for(SensorInfo sensor : DataStorage.getInstance().getSensorList()){
                            if(device.getId().equalsIgnoreCase(sensor.getDeviceId())){
                                String command = getCorrectReadCommand(sensor.getType());
                                System.out.println("Method: getStateOfAllSensors. Sending GET, SensorId: " + sensor.getType() );
                                getSensorInformation(command, sensor.getId(), DataStorage.getInstance().getUserId(), room.getHomeServerId());
                            }
                        }
                    }
                }
            }
        }
    }

    private String getCorrectReadCommand(String sensorType){
        String command = "";
        if(sensorType.equalsIgnoreCase("attictemp")){
            command = "110000000";
        } else if(sensorType.equalsIgnoreCase("roomtemp")){
            command = "120000000";
        } else if(sensorType.equalsIgnoreCase("outdoortemp")){
            command = "130000000";
        } else if(sensorType.equalsIgnoreCase("powerconsumption")){
            command = "140000000";
        } else if(sensorType.equalsIgnoreCase("alarm")){
            command = "160000000";
        } else if(sensorType.equalsIgnoreCase("stove")){
            command = "180000000";
        } else if(sensorType.equalsIgnoreCase("window")){
            command = "190000000";
        } else if(sensorType.equalsIgnoreCase("atticfan")){
            command = "220000000";
        } else if(sensorType.equalsIgnoreCase("indoorlamp")){
            command = "250000000";
        } else if(sensorType.equalsIgnoreCase("outdoorlamp")){
            command = "270000000";
        }
        return command;
    }

    private String getCorrectPOSTCommand(String sensorType){
        String command = "";
        if(sensorType.equalsIgnoreCase("alarm")){
            command = "17000";
        } else if(sensorType.equalsIgnoreCase("atticfan")){
            command = "23000";
        } else if(sensorType.equalsIgnoreCase("indoorlamp")){
            command = "26000";
        } else if(sensorType.equalsIgnoreCase("outdoorlamp")){
            command = "28000";
        }
        return command;
    }

    public void setAtticTemp(String sensorId, String value){
        int dataValue = ((Integer.parseInt(value)/5)/100) * 1024;
        System.out.println("setAtticTemp, dataValue = " + dataValue);
        String dataValueString = "" + dataValue;
        String addZeros = "";
        if(dataValueString.length() < 4){
            int loops = 4 - dataValueString.length();

            for(int i = 0; i < loops; i++ ){
                addZeros += "0";
            }
        }
        postToServer("11100" + addZeros + dataValueString , sensorId, DataStorage.getInstance().getUserId(), DataStorage.getInstance().getChosenHouseId() );
    }

    public void setRadiatorTemp(String sensorId, String value){
        int dataValue = ((Integer.parseInt(value)/5)/100) * 1024;
        System.out.println("setAtticTemp, dataValue = " + dataValue);
        String dataValueString = "" + dataValue;
        String addZeros = "";
        if(dataValueString.length() < 4){
            int loops = 4 - dataValueString.length();

            for(int i = 0; i < loops; i++ ){
                addZeros += "0";
            }
        }
        postToServer("12200" + addZeros + dataValueString , sensorId, DataStorage.getInstance().getUserId(), DataStorage.getInstance().getChosenHouseId() );
    }

    public void setStateOfSensor(String sensorId){
        String command = "";
        String value = "";
        for(SensorInfo sensor : DataStorage.getInstance().getSensorList()){
            if(sensor.getId().equalsIgnoreCase(sensorId)){
                command = getCorrectPOSTCommand(sensor.getType());
                if(sensor.getValue().equalsIgnoreCase("0")){
                    value = "1";
                }else if(sensor.getValue().equalsIgnoreCase("1")){
                    value = "0";
                }
            }
        }
        postToServer(command+ "000" + value , sensorId, DataStorage.getInstance().getUserId(), DataStorage.getInstance().getChosenHouseId() );
    }

}
