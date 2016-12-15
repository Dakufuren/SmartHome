package example.comtest.smarthome;

/**
 * Created by Henrik on 2016-11-29.
 */

public class gridButton {
    private Integer buttonLink;
    private String buttonText;
    private boolean buttonActiveStatus;
    private int currentPosition;


    public gridButton(Integer buttonLink, String buttonText, boolean buttonActiveStatus, int currentPosition) {
        this.buttonLink = buttonLink;
        this.buttonText = buttonText;
        this.buttonActiveStatus = buttonActiveStatus;
        this.currentPosition = currentPosition;
    }

    public boolean isButtonActiveStatus() {
        return buttonActiveStatus;
    }

    public void setButtonActiveStatus(boolean buttonActiveStatus) {
        this.buttonActiveStatus = buttonActiveStatus;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Integer getButtonLink() {
        return buttonLink;
    }

    public void setButtonLink(Integer buttonLink) {
        this.buttonLink = buttonLink;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
