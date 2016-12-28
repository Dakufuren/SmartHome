package example.comtest.smarthome;

/**
 * Created by Sweetpink on 2016-12-28.
 */

public class DeviceInfo {

    private String name;
    private String id;
    private String roomId;

    public DeviceInfo(String name, String id, String roomId) {
        this.name = name;
        this.id = id;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
