package com.ucm.tfg.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.register);
        EditText nameInput = findViewById(R.id.name_input);
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);

        sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        editor = sharedPreferences.edit();

        registerButton.setOnClickListener((View view) -> {
            if (!Utils.isNullOrEmpty(nameInput.getText().toString()) &&
                    !Utils.isNullOrEmpty(emailInput.getText().toString()) &&
                    !Utils.isNullOrEmpty(passwordInput.getText().toString())
            ) {
                registerButton.setEnabled(false);
                User user = new User();
                user.setUuid(UUID.randomUUID().toString());
                user.setName(nameInput.getText().toString());
                user.setEmail(emailInput.getText().toString());
                user.setPassword(passwordInput.getText().toString());
                UserService.register(RegisterActivity.this, user, new Service.ClientResponse<User>() {
                    @Override
                    public void onSuccess(User result) {
                        runOnUiThread(() -> {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                            editor.putBoolean(Session.IS_LOGGED, true);
                            editor.apply();
                            registerButton.setEnabled(true);
                            finish();
                        });
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            registerButton.setEnabled(true);
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                        });
                    }
                }, User.class);
            } else {
                Toast.makeText(RegisterActivity.this, getString(R.string.form_not_filled), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
