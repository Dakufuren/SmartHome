package example.comtest.smarthome;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Henrik on 2016-12-27.
 */

public class HouseAdapter extends ArrayAdapter {
    private ArrayList<String> house;

    public HouseAdapter(Context context, ArrayList<String> house) {
        super(context, R.layout.house_row, house);
        this.house = house;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.house_row, parent, false);

        TextView title = (TextView) customView.findViewById(R.id.titleView);

        title.setText(house.get(position));

        if ((position % 2) == 0) {
            customView.setBackgroundColor(Color.parseColor("#801C1C1C"));
        } else {
            customView.setBackgroundColor(Color.parseColor("#800F0F0F"));
        }

        return customView;
    }
}
