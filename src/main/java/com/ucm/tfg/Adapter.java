package com.ucm.tfg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    String[][] data;
    int[] dataImg;

    public Adapter(Context context, String[][] data, int [] images) {
        this.context = context;
        this.data = data;
        this.dataImg = images;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.plans_list_item, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView friends = (TextView) view.findViewById(R.id.friends);

        ImageView photo = (ImageView) view.findViewById(R.id.photo);

        title.setText(data[i][0]);
        friends.setText(data[i][1]);
        photo.setImageResource(dataImg[i]);

        return view;
    }

    @Override
    public int getCount() {
        return dataImg.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
