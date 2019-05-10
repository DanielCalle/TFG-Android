

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
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

import java.util.ArrayList;
import java.util.List;


public class PlanFriendsAdapter extends RecyclerView.Adapter<PlanFriendsAdapter.RecyclerViewHolder> {

    private Activity context;
    private List<Plan> plans;
    private PlanActionListener planActionListener;
    private static int count;

    public PlanFriendsAdapter(Activity context) {
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
                recyclerViewHolder.title.setText(result.getName());
                recyclerViewHolder.date.setText(plan.getDate().toString());

            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        }, Film.class);
        UserService.getUserById(this.context, plan.getCreatorId(), new Service.ClientResponse<User>() {

            @Override
            public void onSuccess(User result) {
                //recyclerViewHolder.from.setText(result.getName());
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        }, User.class);

        if(recyclerViewHolder.num == 0) {
            ++this.count;
            recyclerViewHolder.num = this.count;
        }

        recyclerViewHolder.plan.setText(recyclerViewHolder.planString + " " + recyclerViewHolder.num);


        PlanService.getJoinedUsers(this.context, plan.getId(), new Service.ClientResponse<ArrayList<User>>(){

            @Override
            public void onSuccess(ArrayList<User> result) {
                String users = "";
                PlanUserAdapter planUserAdapter = new PlanUserAdapter(context);
                recyclerViewHolder.users.setAdapter(planUserAdapter);
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
