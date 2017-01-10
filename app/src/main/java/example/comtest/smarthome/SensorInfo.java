package example.comtest.smarthome;

/**
 * Created by Sweetpink on 2016-12-28.
 */

public class SensorInfo {
    private String name;
    private String id;
    private String type;
    private String deviceId;
    private String value;
    private String houseId;

    public static final String sensorTypes = "window,outdoorLamp";


    public SensorInfo(String name, String id, String type, String deviceId, String value, String houseId) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.deviceId = deviceId;
        this.value = value;
        this.houseId = houseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public int getDrawable() {

        switch (this.type) {
            case "window":
                if(value=="0"){
                    return R.drawable.window_closed;
                }
                else if(value=="1"){
                    return R.drawable.window_open;
                }
            case "outdoorLamp":
                if(value=="0"){
                    return R.drawable.outdoor_lamp_off;
                }
                else if(value=="1"){
                    return R.drawable.outdoor_lamp_on;
                }
            case "indoorLamp":
                if(value=="0"){
                    return R.drawable.indoor_lamp_off;
                }
                else if(value=="1"){
                    return R.drawable.indoor_lamp_on;
                }
            case "atticFan":
                return  R.drawable.attic_fan;
            case "stove":
                if(value=="0"){
                    return R.drawable.stove_off;
                }
                else if(value=="1"){
                    return R.drawable.stove_on;
                }
            case "atticTemp":
                    return R.drawable.attic;
            case "roomTemp":
                return R.drawable.temperature;
            case "alarm":
                return R.drawable.burglar;
            case "outdoorTemp":
                return R.drawable.outdoor_temp;
            case "powerConsumtion":
                return R.drawable.power;
            case "House":
                return R.drawable.house;
            default:
                return R.drawable.attic;

        }
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}

