package com.ucm.tfg.adapters;

import android.content.Context;
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
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

import java.util.ArrayList;
import java.util.List;


public class PlanJoinedUsersAdapter extends RecyclerView.Adapter<PlanJoinedUsersAdapter.RecyclerViewHolder> {

    private Context context;
    private List<User> joinedUsers;

    private PlanJoinedUserActionListener planJoinedUserActionListener;

    public PlanJoinedUsersAdapter(Context context) {
        this.context = context;
        joinedUsers = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.plan_joined_user_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int index) {
        User user = joinedUsers.get(index);

        recyclerViewHolder.cardView.setOnClickListener((View v) -> {
            planJoinedUserActionListener.onUserClick(user);
        });

        recyclerViewHolder.title.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return joinedUsers.size();
    }

    public void setData(List<User> data) {
        joinedUsers = data;
        notifyDataSetChanged();
    }

    public void addPlanOnClickListener(PlanJoinedUserActionListener planJoinedUserActionListener) {
        this.planJoinedUserActionListener = planJoinedUserActionListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView image;
        public TextView date;
        public TextView title;
        public TextView users;

        public RecyclerViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardview);
            image = view.findViewById(R.id.image);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
        }
    }

    public interface PlanJoinedUserActionListener {

        void onUserClick(User u);

    }

}
