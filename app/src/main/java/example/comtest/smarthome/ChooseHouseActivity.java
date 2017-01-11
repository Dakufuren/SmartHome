package example.comtest.smarthome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseHouseActivity extends AppCompatActivity {
    ArrayList<String> houses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_house);
        setListView();
    }

    private void setListView() {
        for (int i = 0; i < DataStorage.getInstance().getHomeServerList().size(); i++) {
            houses.add(DataStorage.getInstance().getHomeServerList().get(i).getName());
        }

        HouseAdapter houseAdapter = new HouseAdapter(getApplicationContext(), houses);
        ListView houseListView = (ListView) findViewById(R.id.houseList);
        houseListView.setAdapter(houseAdapter);
        houseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DataStorage.getInstance().setFirstStart(false);
                DataStorage.getInstance().setChosenHouseId(String.valueOf(DataStorage.getInstance().getHomeServerList().get(position).getId()));
                System.out.println("New house picked:   " + DataStorage.getInstance().getHomeServerList().get(position).getName() + "   ___Id is:    " + String.valueOf(DataStorage.getInstance().getHomeServerList().get(position).getId()));
            }
        });
    }
}
