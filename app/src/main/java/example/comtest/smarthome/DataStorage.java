package example.comtest.smarthome;

import java.util.ArrayList;

/**
 * Created by Sweetpink on 2016-12-22.
 */

public class DataStorage {

    private static DataStorage dataStorage;
    private int radiatorTemparature;  //used for the radiator part
    private String tempSensorId; //used in the radiatorActivity
    private String chosenHouseId;
    private String userId;

    public String getAtticOrRadiator() {
        return atticOrRadiator;
    }

    public void setAtticOrRadiator(String atticOrRadiator) {
        this.atticOrRadiator = atticOrRadiator;
    }

    private String atticOrRadiator;
    private boolean firstStart = true;

    private ArrayList<HomeServer> homeServerList = new ArrayList<>();
    private ArrayList<RoomInfo> roomList = new ArrayList<>();
    private ArrayList<DeviceInfo> deviceList = new ArrayList<>();
    private ArrayList<SensorInfo> sensorList = new ArrayList<>();

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

    public boolean isFirstStart() {
        return firstStart;
    }

    public void setFirstStart(boolean firstStart) {
        this.firstStart = firstStart;
    }

    public ArrayList<HomeServer> getHomeServerList() {
        return homeServerList;
    }

    public void addHomeServerToList(String name, String id, String userId ) {
        homeServerList.add(new HomeServer(name, id, userId));
    }

    public ArrayList<RoomInfo> getRoomList() {
        return roomList;
    }

    public ArrayList<DeviceInfo> getDeviceList() {
        return deviceList;
    }

    public ArrayList<SensorInfo> getSensorList() {
        return sensorList;
    }

    public void addRoomToList(String name, String id, String homeServerId ) {
        roomList.add(new RoomInfo(id, name, homeServerId));
    }

    public void addDeviceToList(String name, String id, String roomId ) {
        deviceList.add(new DeviceInfo(name, id, roomId));
    }

    public void addSensorToList(String name, String id, String type, String deviceId, String value,String houseId ) {
        sensorList.add(new SensorInfo(name, id, type, deviceId, value,houseId));
    }

    public String getTempSensorId() {
        return tempSensorId;
    }

    public void setTempSensorId(String tempSensorId) {
        this.tempSensorId = tempSensorId;
    }

    public String getChosenHouseId() {
        return chosenHouseId;
    }

    public void setChosenHouseId(String chosenHouseId) {
        this.chosenHouseId = chosenHouseId;
    }
}
