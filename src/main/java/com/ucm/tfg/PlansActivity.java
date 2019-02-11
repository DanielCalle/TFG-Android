package com.ucm.tfg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class PlansActivity extends AppCompatActivity {

    ListView list;
    String [][] data = {{"Moana","Diego, Carlos"},{"Deadpool","Zihao, Daniel"}};

    int[] dataImg = {R.drawable.moana_poster, R.drawable.deadpool_poster};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        list = (ListView) findViewById(R.id.plans_list);

        list.setAdapter(new Adapter(this, data, dataImg));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalles = new Intent(view.getContext(), PlanListItem.class);
                visorDetalles.putExtra("TITLE", data[position][0]);
                visorDetalles.putExtra("FRIENDS", data[position][4]);
                startActivity(visorDetalles);
            }
        });
    }

    public void lanzaAr(View view){
        Intent ar = new Intent(this, UnityPlayerActivity.class);
        startActivity(ar);
    }

    public void goSaves(View view){
        Intent saves = new Intent(this, SavesActivity.class);
        startActivity(saves);
    }

    public void testAction(View view){

    }
}
