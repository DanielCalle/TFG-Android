package com.ucm.tfg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends BaseAdapter {

    private List<Film> films;
    private Context context;
    private FilmActionListener filmActionListener;

    public FilmAdapter(Context context) {
        this.context = context;
        films = new ArrayList<>();
    }


    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<Film> data) {
        films = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);

        ImageView image = view.findViewById(R.id.film_poster);
        TextView text = view.findViewById(R.id.text);

        Film film = (Film)this.getItem(position);

        Picasso.get()
                .load(film
                        .getImageURL()
                ).fit()
                .into(image);

        image.setOnClickListener((View v) -> {
            if (filmActionListener != null) {
                filmActionListener.onFilmClick(film, v);
            }
        });

        return view;
    }

    public void addPlanOnClickListener(FilmActionListener filmActionListener) {
        this.filmActionListener = filmActionListener;
    }

    public interface FilmActionListener {

        void onFilmClick(Film film, View v);

    }
}
