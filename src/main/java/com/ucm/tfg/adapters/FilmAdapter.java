package com.ucm.tfg.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

import static android.support.v4.content.ContextCompat.startActivity;

public class FilmAdapter extends BaseAdapter {

    private List<Film> films;
    private Context context;

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

        ImageView image = view.findViewById(R.id.imageView);
        TextView text = view.findViewById(R.id.text);

        Film film = (Film)this.getItem(position);

        Picasso.get()
                .load(film
                        .getImageURL()
                ).fit()
                .into(image);

        image.setOnClickListener((View v) -> {
            Intent intentInfo = new Intent(Intent.ACTION_VIEW);
            intentInfo.setData(Uri.parse(film.getInfoURL()));
            startActivity(this.context, intentInfo, Bundle.EMPTY);
        });

        return view;
    }
}
