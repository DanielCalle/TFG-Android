package com.ucm.tfg.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ucm.tfg.R;
import com.ucm.tfg.adapters.FormInputAdapter;

public class FormActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FormInputAdapter formInputAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        for (String key : getIntent().getExtras().keySet()) {
            Log.i("dadad", key);
        }

        recyclerView = findViewById(R.id.inputs);
        formInputAdapter = new FormInputAdapter(FormActivity.this);
        recyclerView.setAdapter(formInputAdapter);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener((View v) -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("res", "result");
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

}
