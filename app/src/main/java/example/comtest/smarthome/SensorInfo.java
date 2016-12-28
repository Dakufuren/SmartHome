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

    public SensorInfo(String name, String id, String type, String deviceId, String value) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.deviceId = deviceId;
        this.value = value;
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
}
