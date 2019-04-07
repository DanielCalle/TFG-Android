package com.ucm.tfg.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.User;

import java.util.ArrayList;
import java.util.List;

public class PlanUserAdapter extends RecyclerView.Adapter<PlanUserAdapter.RecyclerViewHolder> {

    private Activity context;
    private List<User> users;

    public PlanUserAdapter(Activity context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlanUserAdapter.RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.plan_user_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlanUserAdapter.RecyclerViewHolder recyclerViewHolder, int index) {
        User user = users.get(index);
        Picasso.get()
                .load(user.getImageURL()
                )
                .resize(600, 200)
                .centerCrop()
                .into(recyclerViewHolder.userImage);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView userImage;

        public RecyclerViewHolder(View view) {
            super(view);
            userImage = view.findViewById(R.id.imageUser);
        }
    }
}
