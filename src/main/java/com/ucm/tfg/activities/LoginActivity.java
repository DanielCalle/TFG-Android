package com.ucm.tfg.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.entities.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login);
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);

        SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        loginButton.setOnClickListener((View view) -> {
            User user = new User();
            user.setEmail(emailInput.getText().toString());
            user.setPassword(passwordInput.getText().toString());
            editor.putBoolean(Session.IS_LOGGED, true);
            editor.apply();
            finish();
        });
    }
}
