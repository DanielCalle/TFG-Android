package com.ucm.tfg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucm.tfg.R;

public class FormInputAdapter extends RecyclerView.Adapter<FormInputAdapter.RecyclerViewHolder> {

    private Context context;

    public FormInputAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FormInputAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.form_input_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FormInputAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView label;

        public RecyclerViewHolder(View view) {
            super(view);
            label = view.findViewById(R.id.label);
        }
    }
}
