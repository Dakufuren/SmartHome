package example.comtest.smarthome;

import java.util.ArrayList;

/**
 * Created by Sweetpink on 2016-12-22.
 */

public class DataStorage {

    private static DataStorage dataStorage;
    private int radiatorTemparature;  //used for the radiator part
    private String userId;

    private ArrayList<String> homeServerList = new ArrayList<>();

    private DataStorage(){
    }

    public static DataStorage getInstance(){
        if(dataStorage == null){
            dataStorage = new DataStorage();
        }

        return dataStorage;
    }

    public int getRadiatorTemparature() {
        return radiatorTemparature;
    }

    public void setRadiatorTemparature(int radiatorTemparature) {
        this.radiatorTemparature = radiatorTemparature;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getHomeServerList() {
        return homeServerList;
    }

    public void addHomeServerToList(String addHomeServer) {
        homeServerList.add(addHomeServer);
    }
}
