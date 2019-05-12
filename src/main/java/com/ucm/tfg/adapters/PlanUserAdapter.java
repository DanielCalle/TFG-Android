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

// This is used for recycler view, see its doc in android developer
public class PlanUserAdapter extends RecyclerView.Adapter<PlanUserAdapter.RecyclerViewHolder> {

    private Activity context;
    private List<User> users;
    private UserActionListener userActionListener;

    public PlanUserAdapter(Activity context) {
        this.context = context;
        this.users = new ArrayList<>();
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
                .resize(100, 100)
                .into(recyclerViewHolder.userImage);

        recyclerViewHolder.userImage.setOnClickListener((View v) -> {
            if (userActionListener != null) {
                userActionListener.onUserClick(user, recyclerViewHolder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public void setData(List<User> data) {
        users = data;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView userImage;

        public RecyclerViewHolder(View view) {
            super(view);
            userImage = view.findViewById(R.id.imageUser);
        }
    }


    public void addUserOnClickListener(UserActionListener userActionListener) {
        this.userActionListener = userActionListener;
    }

    public interface UserActionListener {

        void onUserClick(User u, RecyclerViewHolder recyclerViewHolder);

    }
}
