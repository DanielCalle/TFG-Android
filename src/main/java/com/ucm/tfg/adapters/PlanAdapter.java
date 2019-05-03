package com.ucm.tfg.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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
import com.ucm.tfg.activities.PlanActivity;
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
    private List<Plan> friendsPlans;
    private PlanActionListener planActionListener;
    private static int count;

    public PlanAdapter(Activity context) {
        this.context = context;
        this.count = 0;
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
        updateView(plan, recyclerViewHolder);
    }

    public void updateView(Plan plan, @NonNull RecyclerViewHolder recyclerViewHolder){
        FilmService.getFilmById(this.context, plan.getFilmId(), new Service.ClientResponse<Film>() {
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

            }
        }, Film.class);
        

        if(recyclerViewHolder.num == 0) {
            ++this.count;
            recyclerViewHolder.num = this.count;
        }

        recyclerViewHolder.title.setText(plan.getTitle());
        recyclerViewHolder.plan.setText(recyclerViewHolder.planString + " " + recyclerViewHolder.num);


        PlanService.getUsers(this.context, plan.getId(), new Service.ClientResponse<ArrayList<User>>(){

            @Override
            public void onSuccess(ArrayList<User> result) {
                PlanUserAdapter planUserAdapter = new PlanUserAdapter(context);
                recyclerViewHolder.users.setAdapter(planUserAdapter);
                planUserAdapter.setData(result);
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

    public void setPlansData(List<Plan> data) {

        this.plans = data;
        notifyDataSetChanged();
    }

    public void setFriendsPlansData(List<Plan> data) {

        this.friendsPlans = data;
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
        public int num;
        public String planString;

        public RecyclerViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardview);
            image = view.findViewById(R.id.image);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
            users = view.findViewById(R.id.users);
            plan = view.findViewById(R.id.plan);
            planString = "#" + view.getResources().getString(R.string.plan);
            num = 0;
            users.setHasFixedSize(true);
            users.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    public interface PlanActionListener {

        void onPlanClick(Plan p, RecyclerViewHolder recyclerViewHolder);

    }

}
