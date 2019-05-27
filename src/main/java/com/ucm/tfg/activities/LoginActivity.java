package com.ucm.tfg.activities;

import android.content.Intent;
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
import com.ucm.tfg.service.Request;
import com.ucm.tfg.service.UserRequest;

/**
 * The intarface of login
 */
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
            if (!Utils.isNullOrEmpty(emailInput.getText().toString()) &&
                    !Utils.isNullOrEmpty(passwordInput.getText().toString())
            ) {
                loginButton.setEnabled(false);
                User user = new User();
                user.setEmail(emailInput.getText().toString());
                user.setPassword(passwordInput.getText().toString());
                UserRequest.login(LoginActivity.this, user, new Request.ClientResponse<User>() {
                    @Override
                    public void onSuccess(User result) {
                        // When logged writing logged user in session
                        editor.putBoolean(Session.IS_LOGGED, true);
                        editor.putLong(Session.USER, result.getId());
                        editor.apply();
                        Session.user = result;
                        loginButton.setEnabled(true);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            loginButton.setEnabled(true);
                            Toast.makeText(LoginActivity.this, getText(R.string.login_fail), Toast.LENGTH_SHORT).show();
                        });
                    }
                }, User.class);
            } else {
                Toast.makeText(LoginActivity.this, getString(R.string.form_not_filled), Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener((View view) -> {
            // redirect to register a new account
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
