package com.ucm.tfg.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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
import com.ucm.tfg.Utils;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

import java.util.ArrayList;
import java.util.List;


public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Plan> plans;
    private PlanActionListener planActionListener;

    public PlanAdapter(Context context) {
        this.context = context;
        plans = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.plan_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int index) {
        Plan plan = plans.get(index);

        Picasso.get()
                .load(plan
                        .getFilm()
                        .getImageURL()
                )
                .resize(600, 200)
                .centerCrop()
                .into(recyclerViewHolder.image);

        recyclerViewHolder.cardView.setOnClickListener((View v) -> {
            if (planActionListener != null) {
                planActionListener.onPlanClick(plan, recyclerViewHolder);
            }
        });

        recyclerViewHolder.title.setText(plan.getFilm().getName());
        recyclerViewHolder.from.setText(plan.getCreator().getName());

        List<User> joinedUsers = plan.getJoinedUsers();

        String users = "";
        for (int i = 0; i < joinedUsers.size(); i++) {
            users += joinedUsers.get(i).getName() + ", ";
        }
        recyclerViewHolder.to.setText(users);
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void setData(List<Plan> data) {
        plans = data;
        notifyDataSetChanged();
    }

    public void addPlanOnClickListener(PlanActionListener planActionListener) {
        this.planActionListener = planActionListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView image;
        public TextView title;
        public TextView from;
        public TextView to;

        public RecyclerViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardview);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            from = view.findViewById(R.id.from);
            to = view.findViewById(R.id.to);
        }
    }

    public interface PlanActionListener {

        void onPlanClick(Plan p, RecyclerViewHolder recyclerViewHolder);

    }

}
