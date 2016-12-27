package example.comtest.smarthome;

/**
 * Created by Sweetpink on 2016-12-22.
 */

public class DataStorage {

    private static DataStorage dataStorage;
    private int radiatorTemparature;  //used for the radiator part
    private String userId;
    private boolean firstStart = true;
    private boolean[] buttonBooleanArray;

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

    public boolean[] getButtonBooleanArray() {
        return buttonBooleanArray;
    }

    public void setButtonBooleanArray(boolean[] butonBooleanArray) {
        this.buttonBooleanArray = butonBooleanArray;
    }

    public boolean isFirstStart() {
        return firstStart;
    }

    public void setFirstStart(boolean firstStart) {
        this.firstStart = firstStart;
    }
}
