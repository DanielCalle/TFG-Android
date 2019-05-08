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
import com.ucm.tfg.entities.Film;

import java.util.ArrayList;
import java.util.List;

public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.RecyclerViewHolder> {

    private Context context;
    private List<String> data;
    private RecommendationItemAdapter userRecommendations, trendings, premieres;
    private FilmActionListener filmActionListener;

    public RecommendationListAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
        this.data.add(context.getString(R.string.film_recommendations_user));
        this.data.add(context.getString(R.string.film_recommendations_trending));
        this.data.add(context.getString(R.string.film_recommendations_premiere));
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
        recyclerViewHolder.recommendationTitle.setText(data.get(i));
        switch (i) {
            case 0: userRecommendations = recyclerViewHolder.recommendationItemAdapter; break;
            case 1: trendings = recyclerViewHolder.recommendationItemAdapter; break;
            case 2: premieres = recyclerViewHolder.recommendationItemAdapter; break;
        }
        recyclerViewHolder.recommendationItemAdapter.addFilmOnClickListener((Film film, View v) -> {
            filmActionListener.onFilmClick(film, v);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView recommendationTitle;
        public RecyclerView recommendationList;
        public RecommendationItemAdapter recommendationItemAdapter;

        public RecyclerViewHolder(View view){
            super(view);
            recommendationTitle = view.findViewById(R.id.recommendation_title);

            recommendationList = view.findViewById(R.id.recommendation_list);
            recommendationList.setHasFixedSize(true);
            recommendationList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

            recommendationItemAdapter = new RecommendationItemAdapter(view.getContext());
            recommendationList.setAdapter(recommendationItemAdapter);
        }
    }

    public void setRecommendedData(List<Film> data) {
        userRecommendations.setData(data);
    }

    public void setTrendingData(List<Film> data) {
        trendings.setData(data);
    }

    public void setPremiereData(List<Film> data) {
        premieres.setData(data);
    }

    public void addFilmOnClickListener(FilmActionListener filmActionListener) {
        this.filmActionListener = filmActionListener;
    }

    public interface FilmActionListener {

        void onFilmClick(Film film, View v);

    }

}
