package example.comtest.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "SmartHousePrefs";
    ImageAdapter adapter;

    String apiResponse;

    private List<gridButton> gridButtonArrayList = new ArrayList<gridButton>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateGridButtons();

        RecievePubNub.getInstance().setContext(getApplicationContext());
        RecievePubNub.getInstance().subscribe();




        if (FIRST_START == true) {
            requestToApi rta = new requestToApi(getApplicationContext());
            //rta.getFromServerTest();

            FIRST_START = false;
        }




         adapter = new ImageAdapter(this, mThumbIds) ;

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                requestToApi rta = new requestToApi(getApplicationContext());
                if(position == gridButtonArrayList.get(0).getCurrentPosition()){
                    Toast.makeText(MainActivity.this, "Lamp 1: " + LAMP_ONOFF,
                            Toast.LENGTH_SHORT).show();
                    if(LAMP_ONOFF == false){
                        apiResponse = rta.postToServer("260001", "1", "1");
                        View viewItem = gridview.getChildAt(position);
                        if (viewItem != null) {
                            ImageView imgview = (ImageView) viewItem.findViewById(R.id.imageView1);
                            imgview.setImageResource(R.drawable.lamp_on);
                        }

                        LAMP_ONOFF = true;
                        NotificationHandler not = new NotificationHandler(getApplicationContext(), 0, "titel", "HejVärld");
                    }
                    else{
                        apiResponse = rta.postToServer("260000", "1", "1");
                        View viewItem = gridview.getChildAt(position);
                        if (viewItem != null) {
                            ImageView imgview = (ImageView) viewItem.findViewById(R.id.imageView1);
                            imgview.setImageResource(R.drawable.lamp_off);
                        }

                        LAMP_ONOFF = false;
                    }
                }
                else if(position == gridButtonArrayList.get(1).getCurrentPosition()){
                    Toast.makeText(MainActivity.this, "Lamp 2: " + LAMP_ONOFF2,
                            Toast.LENGTH_SHORT).show();
                    if(LAMP_ONOFF2 == false){

                        View viewItem = gridview.getChildAt(position);
                        if (viewItem != null) {
                            ImageView imgview = (ImageView) viewItem.findViewById(R.id.imageView1);
                            imgview.setImageResource(R.drawable.lamp_on2);
                        }

                        LAMP_ONOFF2 = true;
                    }
                    else{
                        View viewItem = gridview.getChildAt(position);
                        if (viewItem != null) {
                            ImageView imgview = (ImageView) viewItem.findViewById(R.id.imageView1);
                            imgview.setImageResource(R.drawable.lamp_off2);
                        }

                        LAMP_ONOFF2 = false;
                    }
                    /*try {
                        //Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
                else if(position == gridButtonArrayList.get(2).getCurrentPosition()){
                    Toast.makeText(MainActivity.this, "Temperature",
                            Toast.LENGTH_SHORT).show();
                    View viewItem = gridview.getChildAt(position);
                    if (viewItem != null) {
                        TextView textview = (TextView) viewItem.findViewById(R.id.textView1);
                        textview.setText("28 degrees");
                    }

                }
                else if(position == gridButtonArrayList.get(3).getCurrentPosition()){
                    View viewItem = gridview.getChildAt(position);
                    if (viewItem != null) {
                        test();
                    }
                }
            }
        });

    }

    private void test(){
        Intent myIntent = new Intent(MainActivity.this, RadiatorActivity.class);
        startActivity(myIntent);
    }


    private Integer[] mThumbIds = {
            R.drawable.lamp_off, R.drawable.lamp_off2,
            R.drawable.temperature, R.drawable.lamp_on2,
    };

    private String[] mThumbTexts = {
           "", "",
           "TEMP", "",
    };

    private void testCaller(GridView gridview){ //For test purpose. Boolean value for each button.
        boolean[] booleanArray = new boolean[3];
        booleanArray[0] = true;
        booleanArray[1] = true;
        booleanArray[2] = false;
        updateTheGridView(booleanArray, gridview);
    }



    private void populateGridButtons(){
        for(int i=0;i<mThumbIds.length;i++){
            gridButton gridButton = new gridButton(mThumbIds[i],mThumbTexts[i],true,i);
            gridButtonArrayList.add(gridButton);
        }
    }
    private void updateTheGridView(boolean[] visibleOnArray, GridView gridView){ //Unnecessary complicated with many loops. Can be optimized with hash maps etc.
        //populateGridButtons(); Behövs inte
        List<String> textList = new ArrayList<String>();
        List<Integer> imageList = new ArrayList<Integer>();
        for(int j =0;j<gridButtonArrayList.size();j++){//resetta till ett värde som inte finns
            gridButtonArrayList.get(j).setCurrentPosition(100);
        }
        for (int i = 0; i < mThumbIds.length; i++) {
            if (visibleOnArray[i]) {
                textList.add(mThumbTexts[i]);
                imageList.add(mThumbIds[i]);

            }
        }
        Integer[] mThumbIds = new Integer[imageList.size()];
        String[] mThumbText = new String[textList.size()];
        for(int i=0;i<textList.size();i++){
            mThumbIds[i] = imageList.get(i);
            mThumbText[i] = textList.get(i);
        }

        for(int h=0;h<mThumbIds.length;h++){
            for(int i=0;i<gridButtonArrayList.size();i++){
                if(gridButtonArrayList.get(i).getButtonLink()==(mThumbIds[h])){
                    gridButtonArrayList.get(i).setCurrentPosition(h);
                    Log.d("see","Changed " +gridButtonArrayList.get(i).getButtonLink().toString() +"   To:   "  + gridButtonArrayList.get(i).getCurrentPosition());
                }
            }

        }
        adapter.setMThumbIds(mThumbIds);
        adapter.setMThumbTexts(mThumbText);
        adapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
    }

}
