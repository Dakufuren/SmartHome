package example.comtest.smarthome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseHouseActivity extends AppCompatActivity {
    ArrayList<String> houses =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_house);
        testFillHouseArray();
        setListView();
    }
    private void setListView(){
        HouseAdapter houseAdapter = new HouseAdapter(getApplicationContext(),houses);
        ListView houseListView = (ListView) findViewById(R.id.houseList);
        houseListView.setAdapter(houseAdapter);
        houseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                testBooleanArraySetter();
                DataStorage.getInstance().setFirstStart(false);
            }
        });
    }

    private void testFillHouseArray(){ //For test purpose.
        houses.add(" 1");
        houses.add(" 2");
        houses.add(" 3");
        houses.add(" 4");
        houses.add(" 5");
    }
    private void testBooleanArraySetter(){ //For test purpose
        boolean[] butonBooleanArray = new boolean[5];
        butonBooleanArray[0] = true;
        butonBooleanArray[1] = false;
        butonBooleanArray[2] = false;
        butonBooleanArray[3] = true;
        butonBooleanArray[4] = true;
        DataStorage.getInstance().setButtonBooleanArray(butonBooleanArray);
    }

}
