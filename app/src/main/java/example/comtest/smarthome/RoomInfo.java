package example.comtest.smarthome;

/**
 * Created by Sweetpink on 2016-12-28.
 */

public class RoomInfo {

    private String Id;
    private String Name;
    private String homeServerId;

    public RoomInfo(String id, String name, String homeServerId) {
        Id = id;
        Name = name;
        this.homeServerId = homeServerId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHomeServerId() {
        return homeServerId;
    }

    public void setHomeServerId(String homeServerId) {
        this.homeServerId = homeServerId;
    }
}
