package com.ucm.tfg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucm.tfg.R;

public class FriendAdapter extends BaseAdapter {

    String [][] data = {
            {"Moana","Diego", "Carlos"},
            {"Deadpool","Carlos", "Zihao"},
            {"1","Diego", "Daniel"},
            {"2","Carlos", "Diego"},
            {"3","Zihao", "Carlos"},
            {"4","Zihao", "Daniel"},
            {"5","Diego", "Carlos"},
            {"8","Diego", "Zihao"},
            {"6","Carlos", "Carlos"},
            {"454","Zihao", "Carlos"},
            {"34","Diego", "Carlos"},
            {"5","Diego", "Zihao"},
            {"64","Diego", "Carlos"},
            {"De77adpool","Carlos", "Diego"},
            {"Moan86a","Diego", "Daniel"},
            {"Dea232dpool","Zihao", "Diego"}
    };
    private Context context;

    public FriendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);

        ImageView image = view.findViewById(R.id.image);
        TextView title = view.findViewById(R.id.title);

        title.setText(data[position][0]);

        return view;
    }
}
