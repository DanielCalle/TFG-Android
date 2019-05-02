package com.ucm.tfg.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Utils;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;

import java.util.ArrayList;
import java.util.List;


public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.RecyclerViewHolder> {

    private Activity context;
    private List<User> friends;
    private FriendActionListener friendActionListener;

    public FriendAdapter(Activity context) {
        this.context = context;
        friends = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.user_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int index) {
        User user = friends.get(index);

        Picasso.get()
                .load(user.getImageURL())
                .fit()
                .into(recyclerViewHolder.userAvatar);

        recyclerViewHolder.userName.setText(user.getName());

        recyclerViewHolder.cardView.setOnClickListener((View v) -> {
            friendActionListener.onFriendClick(user, recyclerViewHolder);
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setFriendData(List<User> data) {
        this.friends = data;
        notifyDataSetChanged();
    }

    public void addPlanOnClickListener(FriendActionListener friendActionListener) {
        this.friendActionListener = friendActionListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public ImageView userAvatar;
        public CardView cardView;

        public RecyclerViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.user_name);
            userAvatar = view.findViewById(R.id.user_avatar);
            cardView = view.findViewById(R.id.cardview);
        }
    }

    public interface FriendActionListener {

        void onFriendClick(User friend, RecyclerViewHolder recyclerViewHolder);

    }

}
