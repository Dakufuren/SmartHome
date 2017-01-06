package example.comtest.smarthome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Albin on 2016-10-18.
 */

public class ImageAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Integer[] mThumbIds;
    private String[] mThumbTexts;

    public ImageAdapter(Context context,Integer[] mThumbIds,String[] mThumbTexts) {
        this.mThumbIds = mThumbIds;
        this.mThumbTexts = mThumbTexts;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public class Holder {
        TextView tv;
        ImageView img;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_gridlist, null, false);
        //rowView = inflater.inflate(R.layout.custom_gridlist, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        System.out.println("Positon: " + position);
        holder.tv.setText(mThumbTexts[position]);
        holder.img.setImageResource(mThumbIds[position]);

        return rowView;
    }

}