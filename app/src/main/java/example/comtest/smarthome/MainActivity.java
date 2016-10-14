package example.comtest.smarthome;

import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "SmartHousePrefs";

    String lamp1Status = null;

    private Button buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.buttonTest);

        SharedPreferences SmartHousePrefs = getSharedPreferences(PREFS, 0);

        RecievePubNub pubnub = new RecievePubNub();
        pubnub.subscribe();

        if (SmartHousePrefs.getString("lamp1", "Nothing found").toLowerCase().equals("on")) {
            //Change the icon for the lamp to show that it is on
            lamp1Status = "on";
        } else if (SmartHousePrefs.getString("lamp1", "Nothing found").toLowerCase().equals("off")) {
            //change the icon for the lamp to show that it is off
            lamp1Status = "off";
        }


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("HEEEEEHHOO");
                requestToApi rta = new requestToApi(getApplicationContext());
                String response;
                if (lamp1Status.toLowerCase().equals("on")) {
                    //posts the opposite of the current state to the database
                    response = rta.postToServer("off", "1", "1");
                    System.out.println(response);
                } else if (lamp1Status.toLowerCase().equals("off")) {
                    //posts the opposite of the current state to the database
                    response = rta.postToServer("on", "1", "1");
                    System.out.println(response);
                }

            }
        });
    }
}
