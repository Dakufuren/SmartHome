package example.comtest.smarthome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Sweetpink on 2016-12-18.
 */

public class RadiatorActivity extends AppCompatActivity {

    private SeekBar RedBar;
    private TextView degreesTV;
    private Button submitButton;
    private requestToApi rta;


    private int progressHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiator);

        rta = new requestToApi(getApplicationContext());

        RedBar = (SeekBar) findViewById(R.id.seekBar2);
        degreesTV = (TextView) findViewById(R.id.displayDegrees);
        submitButton = (Button) findViewById(R.id.button);

        RedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressHolder = (progress/7) + 15;
                degreesTV.setText("" + progressHolder);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //DataStorage.getInstance().setRadiatorTemparature(progressHolder);

                if(DataStorage.getInstance().getAtticOrRadiator().equalsIgnoreCase("Attic")) {
                    rta.setAtticTemp(DataStorage.getInstance().getTempSensorId(), "" + progressHolder);
                }else if(DataStorage.getInstance().getAtticOrRadiator().equalsIgnoreCase("Radiator")){
                    rta.setRadiatorTemp(DataStorage.getInstance().getTempSensorId(), "" + progressHolder);
                }
            finish();
            }
        });



    }
}
