package example.comtest.smarthome;

import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "SmartHousePrefs";


    String apiResponse;

    String lamp1Status = null;

    private Button buttonTest;


    private boolean LAMP_ONOFF = false;
    private boolean LAMP_ONOFF2 = false;
    private boolean FIRST_START = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecievePubNub.getInstance().setContext(getApplicationContext());
        RecievePubNub.getInstance().subscribe();

        if (FIRST_START == true) {
            requestToApi rta = new requestToApi(getApplicationContext());
            rta.getFromServer("250000", "1", "1");
            FIRST_START = false;
        }






        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                requestToApi rta = new requestToApi(getApplicationContext());
                if(position == 0){

                    Toast.makeText(MainActivity.this, "Lamp 1: " + LAMP_ONOFF,
                            Toast.LENGTH_SHORT).show();
                    if(LAMP_ONOFF == false){
                        apiResponse = rta.postToServer("260001", "1", "1");
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.lamp_on);
                        LAMP_ONOFF = true;
                    }
                    else{
                        apiResponse = rta.postToServer("260000", "1", "1");
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.lamp_off);
                        LAMP_ONOFF = false;
                    }
                }
                if(position == 1){
                    Toast.makeText(MainActivity.this, "Lamp 2: " + LAMP_ONOFF,
                            Toast.LENGTH_SHORT).show();
                    if(LAMP_ONOFF2 == false){

                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.lamp_on2);
                        LAMP_ONOFF2 = true;
                    }
                    else{
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.lamp_off2);
                        LAMP_ONOFF2 = false;
                    }
                }
                if(position == 2){
                    Toast.makeText(MainActivity.this, "Temperature",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
