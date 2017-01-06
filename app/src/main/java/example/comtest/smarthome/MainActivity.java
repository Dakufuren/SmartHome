package example.comtest.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    requestToApi rta;

    public static final String PREFS = "SmartHousePrefs";
    ImageAdapter adapter;

    String apiResponse;

    private FragmentTransaction ftr = null;
    private FragmentManager fm = null;

    String lamp1Status = null;

    private Button buttonTest;


    private boolean LAMP_ONOFF = false;
    private boolean LAMP_ONOFF2 = false;
    private boolean FIRST_START = true;

    View rowView;
    ImageView img;
    TextView tv;
    public GridView gridview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populateGridButtons();

        RecievePubNub.getInstance().setContext(getApplicationContext());
        RecievePubNub.getInstance().subscribe();

        //For testing remove when done
        DataStorage.getInstance().setUserId("3");
        DataStorage.getInstance().setChosenHouseId("3");
        if(DataStorage.getInstance().isFirstStart()){
            DataStorage.getInstance().addSensorToList("House","0","House","0","0","house");
        }

        rta = new requestToApi(getApplicationContext(),this);
        rta.getAllHousesFromUser();  //Denna fixar biffen med att hämta info från hemservern med all info, och då menar jag ALLT. Även Stefan Löfvens hemligheter.


        if (FIRST_START == true) {


            FIRST_START = false;
        }



        gridview = (GridView) findViewById(R.id.gridview);

        //gridViewUpdaterVersionTwo(gridview);
        //gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
              //  requestToApi rta = new requestToApi(getApplicationContext(), this);
                SensorInfo sensor = DataStorage.getInstance().getSensorList().get(position);
                System.out.println("Postion in click:  " + position);
                System.out.println("Type in click:  " + sensor.getType());
                switch (sensor.getType()) {
                case "window":
                    break;
                case "outdoorLamp":
                Toast.makeText(MainActivity.this, "Lamp 1: " + LAMP_ONOFF, Toast.LENGTH_SHORT).show();
                if (LAMP_ONOFF == false) {
                    //apiResponse = rta.postToServer("260001", "1", "1");
                    createImage(gridview, "outdoor_lamp_on", position);
                    LAMP_ONOFF = true;
                    NotificationHandler not = new NotificationHandler(getApplicationContext(), 0, "titel", "HejVärld");
                } else {
                    // apiResponse = rta.postToServer("260000", "1", "1");
                    createImage(gridview, "outdoor_lamp_off", position);
                    LAMP_ONOFF = false;
                }
                    break;

                case "indoorLamp":
                    Toast.makeText(MainActivity.this, "Lamp 2: " + LAMP_ONOFF2,
                            Toast.LENGTH_SHORT).show();
                    if (LAMP_ONOFF2 == false) {
                        createImage(gridview, "indoor_lamp_on", position);
                        LAMP_ONOFF2 = true;
                    } else {
                        createImage(gridview, "indoor_lamp_off", position);
                        LAMP_ONOFF2 = false;
                    }
                    break;
                case "atticFan":
                    break;
                case "stove":
                    chooseTemperatureAct("roomTemp");
                    break;
                case "atticTemp":
                    break;
                case "roomTemp":
                    Toast.makeText(MainActivity.this, "Temperature", Toast.LENGTH_SHORT).show();
                    View viewItem = gridview.getChildAt(position);
                    if (viewItem != null) {
                        TextView textview = (TextView) viewItem.findViewById(R.id.textView1);
                        textview.setText("28 degrees");
                    }
                    break;
                case "alarm":
                    break;
                case "outdoorTemp":
                    Toast.makeText(MainActivity.this, "Temperature", Toast.LENGTH_SHORT).show();
                    viewItem = gridview.getChildAt(position);
                    if (viewItem != null) {
                        TextView textview = (TextView) viewItem.findViewById(R.id.textView1);
                        textview.setText("28 degrees");
                    }
                    break;
                case "powerConsumtion":
                    break;
                    case "House":
                        openHouseActivity();
                        break;
                default:



            }

              /*  if (position == ) {
                    Toast.makeText(MainActivity.this, "Lamp 1: " + LAMP_ONOFF, Toast.LENGTH_SHORT).show();
                    if (LAMP_ONOFF == false) {
                        //apiResponse = rta.postToServer("260001", "1", "1");
                        createImage(gridview, "lamp_on", position);
                        LAMP_ONOFF = true;
                        NotificationHandler not = new NotificationHandler(getApplicationContext(), 0, "titel", "HejVärld");
                    } else {
                        // apiResponse = rta.postToServer("260000", "1", "1");
                        createImage(gridview, "lamp_off", position);
                        LAMP_ONOFF = false;
                    }
                } else if (position == gridButtonArrayList.get(1).getCurrentPosition()) {
                    Toast.makeText(MainActivity.this, "Lamp 2: " + LAMP_ONOFF2,
                            Toast.LENGTH_SHORT).show();
                    if (LAMP_ONOFF2 == false) {
                        createImage(gridview, "lamp_on2", position);
                        LAMP_ONOFF2 = true;
                    } else {
                        createImage(gridview, "lamp_off2", position);
                        LAMP_ONOFF2 = false;
                    }
                    /*try {
                        //Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (position == gridButtonArrayList.get(2).getCurrentPosition()) {
                    Toast.makeText(MainActivity.this, "Temperature", Toast.LENGTH_SHORT).show();
                    View viewItem = gridview.getChildAt(position);
                    if (viewItem != null) {
                        TextView textview = (TextView) viewItem.findViewById(R.id.textView1);
                        textview.setText("28 degrees");
                    }

                } else if (position == gridButtonArrayList.get(3).getCurrentPosition()) {
                    View viewItem = gridview.getChildAt(position);
                    if (viewItem != null) {

                    }
                } else if (position == gridButtonArrayList.get(4).getCurrentPosition()) {
                    View viewItem = gridview.getChildAt(position);
                    if (viewItem != null) {
                        testHouseActivity();
                    }
                }
            }
        */}});

    }

    @Override
    public void onResume() {    //On resume lunches on create activity therefore needs to not call grid view change on first set up.
        super.onResume();
        if (DataStorage.getInstance().isFirstStart() == false) {
            gridViewUpdaterVersionTwo(gridview);
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
        if (sensorId.equalsIgnoreCase("roomtemp")) {
            DataStorage.getInstance().setAtticOrRadiator("Radiator");
        } else if (sensorId.equalsIgnoreCase("attictemp")) {
            DataStorage.getInstance().setAtticOrRadiator("Attic");
        }
        Intent myIntent = new Intent(MainActivity.this, RadiatorActivity.class);
        startActivity(myIntent);
    }

    private void openHouseActivity() {
        Intent myIntent = new Intent(MainActivity.this, ChooseHouseActivity.class);
        startActivity(myIntent);
    }


    public void gridViewUpdaterVersionTwo(GridView gridView) {
        ArrayList<SensorInfo> sensorListLocal = new ArrayList<SensorInfo>();
        sensorListLocal = DataStorage.getInstance().getSensorList();
        ArrayList<Integer> mThumbIds = new ArrayList<Integer>();
        ArrayList< String> mThumbText = new ArrayList<String>();

        System.out.println("Choosen house id:   " + DataStorage.getInstance().getChosenHouseId());
        for (int i = 0; i < sensorListLocal.size(); i++) {
            if(DataStorage.getInstance().getSensorList().get(i).getHouseId().equals(DataStorage.getInstance().getChosenHouseId())||DataStorage.getInstance().getSensorList().get(i).getHouseId().equals("house")){
                System.out.println("House Id :   :   " + sensorListLocal.get(i).getHouseId());
                mThumbIds.add(sensorListLocal.get(i).getDrawable());
                if(sensorListLocal.get(i).getValue().equals("0")||sensorListLocal.get(i).getValue().equals("1")){
                    mThumbText.add("");
                }
                else{
                    mThumbText.add(sensorListLocal.get(i).getValue());
                }
            }

        }
        Integer[] mThumbIdsLocalArray = new Integer[mThumbIds.size()];
        String[] mThumbTextLocalArray = new String [mThumbIds.size()];

        for(int i=0;i<mThumbIds.size();i++){
            mThumbIdsLocalArray[i]=mThumbIds.get(i);
            mThumbTextLocalArray[i]=mThumbText.get(i);
        }

        adapter = new ImageAdapter(this,mThumbIdsLocalArray,mThumbTextLocalArray);

        adapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
        }

}
