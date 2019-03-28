package com.ucm.tfg.adapters;

import android.app.Activity;
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
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

import java.util.ArrayList;
import java.util.List;


public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.RecyclerViewHolder> {

    private Activity context;
    private List<Plan> plans;
    private PlanActionListener planActionListener;

    public PlanAdapter(Activity context) {
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
        FilmService.getFilmById(this.context, plan.getFilmUuid(), new Service.ClientResponse<Film>() {
            @Override
            public void onSuccess(Film result) {
                Picasso.get()
                        .load(result
                                .getImageURL()
                        )
                        .resize(600, 200)
                        .centerCrop()
                        .into(recyclerViewHolder.image);
                recyclerViewHolder.title.setText(result.getName());
            }

            @Override
            public void onError(String error) {

            }
        }, Film.class);

        UserService.getUserById(this.context, plan.getCreatorUuid(), new Service.ClientResponse<User>() {

            @Override
            public void onSuccess(User result) {
                recyclerViewHolder.from.setText(result.getName());
            }

            @Override
            public void onError(String error) {

            }
        }, User.class);


        PlanService.getJoinedUsers(this.context, plan.getId(), new Service.ClientResponse<ArrayList<User>>(){

            @Override
            public void onSuccess(ArrayList<User> result) {
                String users = "";
                for (int i = 0; i < result.size(); i++) {
                    if(i < result.size() - 1) {
                        users += result.get(i).getName() + ", ";
                    } else{
                        users += result.get(i).getName();
                    }
                }
                recyclerViewHolder.to.setText(users);
            }

            @Override
            public void onError(String error) {

            }
        });
        recyclerViewHolder.cardView.setOnClickListener((View v) -> {
            if (planActionListener != null) {
                planActionListener.onPlanClick(plan, recyclerViewHolder);
            }
        });

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
