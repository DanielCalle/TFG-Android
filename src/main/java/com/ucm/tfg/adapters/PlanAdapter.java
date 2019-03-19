package com.ucm.tfg.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.ImageConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.RecyclerViewHolder> {

    String [][] dataa = {
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
    private JSONArray data;

    public PlanAdapter(Context context) {
        this.context = context;
        data = new JSONArray();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plan_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        try {
            JSONObject jsonObject = data.getJSONObject(i);
            JSONObject creator = jsonObject.getJSONObject("creator");

            ObjectMapper objectMapper = new ObjectMapper();
            Film film = objectMapper.readValue(jsonObject.getJSONObject("film").toString(), Film.class);

            ImageConverter imageConverter = new ImageConverter();
            imageConverter.convert(film.getImage(), recyclerViewHolder.image);

            recyclerViewHolder.title.setText(film.getName());
            recyclerViewHolder.from.setText(creator.getString("name"));
            recyclerViewHolder.to.setText(film.getdirector());
        }
        catch (Exception e) {
            Log.e("Error", "Exception: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public void setData(JSONArray jsonArray) {
        data = jsonArray;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView from;
        public TextView to;

        public RecyclerViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            from = view.findViewById(R.id.from);
            to = view.findViewById(R.id.to);
        }
    }

}
