package example.comtest.smarthome;

/**
 * Created by Sweetpink on 2016-12-28.
 */

public class HomeServer {
    private String name;
    private String id;
    private String userId;

    public HomeServer(String homeServerName, String homeServerId, String userId) {
        this.name = homeServerName;
        this.id = homeServerId;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
