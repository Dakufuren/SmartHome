package example.comtest.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private requestToApi rta;
    private String apiResponse;


    private ImageAdapter adapter;
    private boolean FIRST_START = true;

    public GridView gridview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecievePubNub.getInstance().setContext(getApplicationContext());
        RecievePubNub.getInstance().subscribe();

        //For testing remove when done
        DataStorage.getInstance().setUserId("3");
        DataStorage.getInstance().setChosenHouseId("3");
        if (DataStorage.getInstance().isFirstStart()) {
            DataStorage.getInstance().addSensorToList("House", "0", "House", "0", "0", "house");
        }

        rta = new requestToApi(getApplicationContext(), this);
        rta.getAllHousesFromUser();  //Denna fixar biffen med att hämta info från hemservern med all info, och då menar jag ALLT. Även Stefan Löfvens hemligheter.

        if (FIRST_START == true) {
            FIRST_START = false;
        }
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //requestToApi rta = new requestToApi(getApplicationContext(), this);
                SensorInfo sensor = DataStorage.getInstance().getSensorList().get(position);
                switch (sensor.getType()) {
                    case "window":
                        changeSensorState(sensor, position, "window_open", "window_closed", true);
                        break;

                    case "outdoorLamp":
                        changeSensorState(sensor, position, "outdoor_lamp_on", "outdoor_lamp_off", true);
                        break;

                    case "indoorLamp":
                        changeSensorState(sensor, position, "indoor_lamp_on", "indoor_lamp_off", true);
                        break;

                    case "atticFan":
                        changeSensorState(sensor, position, "", "", false);
                        break;

                    case "stove":
                        changeSensorState(sensor, position, "stove_on", "stove_off", true);
                        break;

                    case "atticTemp":
                        chooseTemperatureAct("atticTemp");
                        // openTextViewAndToast(position, Integer.toString(DataStorage.getInstance().getRadiatorTemparature()), "roomTemp");
                        break;

                    case "roomTemp":
                        chooseTemperatureAct("roomTemp");
                        // openTextViewAndToast(position, Integer.toString(DataStorage.getInstance().getRadiatorTemparature()), "roomTemp");
                        break;

                    case "alarm":
                        changeSensorState(sensor, position, "", "", false);
                        openTextViewAndToast(position, sensor.getValue(), "alarm");
                        break;

                    case "outdoorTemp":
                        openTextViewAndToast(position, sensor.getValue(), "outdoorTemp");
                        break;

                    case "powerConsumtion":
                        openTextViewAndToast(position, sensor.getValue(), "powerConsumtion");
                        break;

                    case "House":
                        openHouseActivity();
                        break;

                    default:
                }
            }

            private void openTextViewAndToast(int position, String textViewText, String toastText) {
                Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                View viewItem = gridview.getChildAt(position);
                if (viewItem != null) {
                    TextView textview = (TextView) viewItem.findViewById(R.id.textView1);
                    textview.setText(textViewText);
                }
            }
        });
    }

    private void changeSensorState(SensorInfo sensor, int position, String onDrawableValue, String ofDrawableValue, Boolean imageChangeOnOFF) {
        if (sensor.getValue().equals("0")) {
            if (imageChangeOnOFF) {
                createImage(gridview, onDrawableValue, position);
            }
            DataStorage.getInstance().getSensorList().get(position).setValue("1");

        } else {
            if (imageChangeOnOFF) {
                createImage(gridview, ofDrawableValue, position);
            }
            DataStorage.getInstance().getSensorList().get(position).setValue("0");
        }
        rta.setStateOfSensor(sensor.getType());
    }


    @Override
    public void onResume() {    //On resume lunches on create activity therefore needs to not call grid view change on first set up.
        super.onResume();
        if (DataStorage.getInstance().isFirstStart() == false) {
            gridViewUpdaterVersion(gridview);
        }
    }

    private void createImage(GridView gridView, String drawableValue, int position) {
        View viewItem = gridView.getChildAt(position);
        if (viewItem != null) {
            ImageView imgview = (ImageView) viewItem.findViewById(R.id.imageView1);
            imgview.setImageResource((getResources().getIdentifier(drawableValue, "drawable", getPackageName())));
        }
    }

    private void chooseTemperatureAct(String sensorId) {
        if (sensorId.equalsIgnoreCase("roomTemp")) {
            DataStorage.getInstance().setAtticOrRadiator("Radiator");
        } else if (sensorId.equalsIgnoreCase("atticTemp")) {
            DataStorage.getInstance().setAtticOrRadiator("Attic");
        }
        Intent myIntent = new Intent(MainActivity.this, RadiatorActivity.class);
        startActivity(myIntent);
    }

    private void openHouseActivity() {
        Intent myIntent = new Intent(MainActivity.this, ChooseHouseActivity.class);
        startActivity(myIntent);
    }


    public void gridViewUpdaterVersion(GridView gridView) {
        ArrayList<SensorInfo> sensorListLocal = new ArrayList<SensorInfo>();
        sensorListLocal = DataStorage.getInstance().getSensorList();
        ArrayList<Integer> mThumbIds = new ArrayList<Integer>();
        ArrayList<String> mThumbText = new ArrayList<String>();

        System.out.println("Choosen house id:   " + DataStorage.getInstance().getChosenHouseId());
        for (int i = 0; i < sensorListLocal.size(); i++) {
            if (DataStorage.getInstance().getSensorList().get(i).getHouseId().equals(DataStorage.getInstance().getChosenHouseId()) || DataStorage.getInstance().getSensorList().get(i).getHouseId().equals("house")) {
                System.out.println("House Id :   :   " + sensorListLocal.get(i).getHouseId());
                mThumbIds.add(sensorListLocal.get(i).getDrawable());
                if (sensorListLocal.get(i).getValue().equals("0") || sensorListLocal.get(i).getValue().equals("1")) {
                    mThumbText.add("");
                } else {
                    mThumbText.add(sensorListLocal.get(i).getValue());
                }
            }

        }
        Integer[] mThumbIdsLocalArray = new Integer[mThumbIds.size()];
        String[] mThumbTextLocalArray = new String[mThumbIds.size()];

        for (int i = 0; i < mThumbIds.size(); i++) {
            mThumbIdsLocalArray[i] = mThumbIds.get(i);
            mThumbTextLocalArray[i] = mThumbText.get(i);
        }

        adapter = new ImageAdapter(this, mThumbIdsLocalArray, mThumbTextLocalArray);

        adapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
    }

}
