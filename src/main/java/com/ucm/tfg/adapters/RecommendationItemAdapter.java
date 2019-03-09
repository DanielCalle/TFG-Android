package com.ucm.tfg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucm.tfg.R;

public class RecommendationItemAdapter extends RecyclerView.Adapter<RecommendationItemAdapter.RecyclerViewHolder> {

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

    public RecommendationItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.recommendation_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.title.setText(data[i][0]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;

        public RecyclerViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
        }
    }

}
