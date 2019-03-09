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

public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.RecyclerViewHolder> {

    String [] data = {
            "Recomendados para ti",
            "Juegos que podrian interesarte",
            "Sugerencias personalizadas"
    };

    private Context context;

    public RecommendationListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recommendation_list, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.recommendationTitle.setText(data[i]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView recommendationTitle;
        public RecyclerView recommendationList;

        public RecyclerViewHolder(View view){
            super(view);
            recommendationTitle = view.findViewById(R.id.recommendation_title);

            recommendationList = view.findViewById(R.id.recommendation_list);
            recommendationList.setHasFixedSize(true);
            recommendationList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

            recommendationList.setAdapter(new RecommendationItemAdapter(view.getContext()));
        }
    }

}
