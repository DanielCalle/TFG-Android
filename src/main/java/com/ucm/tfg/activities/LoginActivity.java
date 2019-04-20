package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

public class LoginActivity extends AppCompatActivity {

    private final static int REGISTER_REQUEST = 1;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login);
        Button registerButton = findViewById(R.id.register);
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);

        sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        editor = sharedPreferences.edit();

        loginButton.setOnClickListener((View view) -> {
            User user = new User();
            user.setEmail(emailInput.getText().toString());
            user.setPassword(passwordInput.getText().toString());
            UserService.login(LoginActivity.this, user, new Service.ClientResponse<User>() {
                @Override
                public void onSuccess(User result) {
                    editor.putBoolean(Session.IS_LOGGED, true);
                    editor.apply();
                    finish();
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, getText(R.string.login_fail), Toast.LENGTH_SHORT).show();
                    });
                }
            }, User.class);
        });

        registerButton.setOnClickListener((View view) -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REGISTER_REQUEST);
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REGISTER_REQUEST:
                if (sharedPreferences.getBoolean(Session.IS_LOGGED, false)) {
                    finish();
                }
                break;
        }
    }
}