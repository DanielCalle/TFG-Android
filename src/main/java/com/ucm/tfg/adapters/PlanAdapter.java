package com.ucm.tfg.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucm.tfg.R;
import com.ucm.tfg.Utils;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Plan> plans;

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


        Utils.convertImage(plan.getFilm().getImage(), recyclerViewHolder.image);

        recyclerViewHolder.title.setText(plan.getFilm().getName());
        recyclerViewHolder.from.setText(plan.getCreator().getName());

        List<User> joinedUsers = plan.getJoinedUsers();

        String users = "";
        for(int i = 0; i < joinedUsers.size(); i++){
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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView from;
        public TextView to;

        public RecyclerViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            from = view.findViewById(R.id.from);
            to = view.findViewById(R.id.to);
        }
    }

}
