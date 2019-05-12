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

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;

import java.util.ArrayList;
import java.util.List;

// This is used for recycler view, see its doc in android developer
public class RecommendationItemAdapter extends RecyclerView.Adapter<RecommendationItemAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Film> data;
    private FilmActionListener filmActionListener;

    public RecommendationItemAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
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
        Film film = data.get(i);

        recyclerViewHolder.title.setText(film.getName());
        Picasso.get()
                .load(film.getImageURL()
                )
                .resize(400, 600)
                .into(recyclerViewHolder.image);

        recyclerViewHolder.image.setOnClickListener((View v) -> {
            if (filmActionListener != null) {
                filmActionListener.onFilmClick(film, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Film> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;

        public RecyclerViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.film_poster);
            title = view.findViewById(R.id.title);
        }
    }


    public void addFilmOnClickListener(FilmActionListener filmActionListener) {
        this.filmActionListener = filmActionListener;
    }

    public interface FilmActionListener {

        void onFilmClick(Film film, View v);

    }

}
