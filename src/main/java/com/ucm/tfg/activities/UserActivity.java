package com.ucm.tfg.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Friendship;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.entities.UserFilm;
import com.ucm.tfg.service.FriendshipService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserFilmService;
import com.ucm.tfg.views.ExpandableTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserActivity extends AppCompatActivity {

    private final static String LOGTAG = "UserActivity";

    private enum FriendStatus {
        FRIEND,
        REQUEST,
        RESPONSE,
        NONE
    }

    private ActionBar actionBar;
    private ImageView userAvatar;
    private Button friendStatusButton;
    private Button acceptRequestButton;
    private Button declineRequestButton;
    private FriendStatus friendStatus;
    private LinearLayout responseLayout;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        user = (User) getIntent().getExtras().getSerializable("user");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(user.getName());
        }

        userAvatar = findViewById(R.id.user_avatar);
        friendStatusButton = findViewById(R.id.friend_status);
        acceptRequestButton = findViewById(R.id.accept_request);
        declineRequestButton = findViewById(R.id.decline_request);

        responseLayout = findViewById(R.id.request);

        FriendshipService.areFriends(UserActivity.this, Session.user.getId(), user.getId(), new Service.ClientResponse<Friendship>() {
            @Override
            public void onSuccess(Friendship result) {
                if (result == null) {
                    friendStatus = FriendStatus.NONE;
                } else {
                    friendStatus = result.getActive() ? FriendStatus.FRIEND : (result.getRequesterId() == Session.user.getId()) ? FriendStatus.REQUEST : FriendStatus.RESPONSE;
                }
                showResponseButtons(false);
                switch (friendStatus) {
                    case NONE:
                        friendStatusButton.setText(R.string.friend_send_request);
                        break;
                    case FRIEND:
                        friendStatusButton.setText(R.string.friend_remove);
                        break;
                    case REQUEST:
                        friendStatusButton.setText(R.string.friend_requesting);
                        break;
                    case RESPONSE:
                        showResponseButtons(true);
                        break;
                }
            }

            @Override
            public void onError(String error) {

            }
        }, Friendship.class);

        Picasso.get()
                .load(user.getImageURL())
                .into(userAvatar);

        TextView userName = findViewById(R.id.user_name);
        userName.setText(user.getName());
    }

    private void showResponseButtons(boolean show) {
        responseLayout.setEnabled(show);
        responseLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        friendStatusButton.setEnabled(!show);
        friendStatusButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
