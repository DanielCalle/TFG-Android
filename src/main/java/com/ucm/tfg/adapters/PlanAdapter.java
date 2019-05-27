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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Utils;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmRequest;
import com.ucm.tfg.service.PlanRequest;
import com.ucm.tfg.service.Request;

import java.util.ArrayList;
import java.util.List;

// This is used for recycler view, see its doc in android developer
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
        FilmRequest.getFilmById(this.context, plan.getFilmId(), new Request.ClientResponse<Film>() {
            @Override
            public void onSuccess(Film result) {
                Picasso.get()
                        .load(result
                                .getImageURL()
                        )
                        .resize(600, 200)
                        .centerCrop()
                        .into(recyclerViewHolder.image);
                recyclerViewHolder.date.setText(Utils.dateFormat(plan.getDate()));

            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        }, Film.class);

        recyclerViewHolder.title.setText(plan.getTitle());
        recyclerViewHolder.plan.setText("#" + context.getResources().getString(R.string.plan) + " " + (index + 1));

        PlanRequest.getUsers(this.context, plan.getId(), new Request.ClientResponse<ArrayList<User>>(){

            @Override
            public void onSuccess(ArrayList<User> result) {
                PlanUserAdapter planUserAdapter = new PlanUserAdapter(context);
                recyclerViewHolder.users.setAdapter(planUserAdapter);
                planUserAdapter.addUserOnClickListener((User u, PlanUserAdapter.RecyclerViewHolder rvh) -> {
                    planActionListener.onJoinedUserClick(u, rvh);
                });
                planUserAdapter.setData(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
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

    public void setPlansData(List<Plan> data) {
        this.plans = data;
        notifyDataSetChanged();
    }

    public void setFriendsPlansData(List<Plan> data) {
        this.plans.addAll(data);
        notifyDataSetChanged();
    }

    public void addPlanOnClickListener(PlanActionListener planActionListener) {
        this.planActionListener = planActionListener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView image;
        public TextView date;
        public TextView title;
        public TextView plan;
        public RecyclerView users;

        public RecyclerViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardview);
            image = view.findViewById(R.id.image);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
            users = view.findViewById(R.id.users);
            plan = view.findViewById(R.id.plan);
            users.setHasFixedSize(true);
            users.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    public interface PlanActionListener {

        void onPlanClick(Plan p, RecyclerViewHolder recyclerViewHolder);

        void onJoinedUserClick(User u, PlanUserAdapter.RecyclerViewHolder recyclerViewHolder);

    }

}
