package com.ucm.tfg.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ucm.tfg.R;
import com.ucm.tfg.adapters.FormInputAdapter;

import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FormInputAdapter formInputAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Map<Integer, Pair<String, String>> map = new HashMap<>();
        Bundle dataBundle = getIntent().getExtras();

        int count = 0;
        for (String key : dataBundle.keySet()) {
            map.put(count, new Pair<>(key, dataBundle.getString(key)));
            count++;
        }

        recyclerView = findViewById(R.id.inputs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FormActivity.this));
        formInputAdapter = new FormInputAdapter(FormActivity.this);
        recyclerView.setAdapter(formInputAdapter);

        formInputAdapter.setData(map);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener((View v) -> {
            Map<String, String> result = formInputAdapter.getResult();
            if (result.size() == map.size()) {
                Intent resultIntent = new Intent();
                for (String key : result.keySet()) {
                    resultIntent.putExtra(key, result.get(key));
                }
                setResult(Activity.RESULT_OK, resultIntent);
            }
            finish();
        });
    }

}
