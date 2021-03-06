package com.ucm.tfg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.adapters.PlanAdapter;
import com.ucm.tfg.adapters.PlanUserAdapter;
import com.ucm.tfg.entities.Friendship;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.requests.FriendshipRequest;
import com.ucm.tfg.requests.Request;
import com.ucm.tfg.requests.UserRequest;

import java.util.ArrayList;

/**
 * Interface for a given user, could be either friend or not
 */
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
    private PlanAdapter planAdapter;

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.plans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));

        planAdapter = new PlanAdapter(UserActivity.this);
        planAdapter.addPlanOnClickListener(new PlanAdapter.PlanActionListener() {
            @Override
            public void onPlanClick(Plan p, PlanAdapter.RecyclerViewHolder recyclerViewHolder) {
                // this is used for an animation effect when changing interfaces
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                                UserActivity.this,
                                Pair.create(recyclerViewHolder.image, "film_poster")
                        );
                Intent i = new Intent(UserActivity.this, PlanActivity.class);
                i.putExtra("plan", p);
                startActivity(i, optionsCompat.toBundle());
            }

            @Override
            public void onJoinedUserClick(User u, PlanUserAdapter.RecyclerViewHolder recyclerViewHolder) {
                // this is used for an animation effect when changing interfaces
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                                UserActivity.this,
                                Pair.create(recyclerViewHolder.userImage, "user_avatar")
                        );
                Intent i = new Intent(UserActivity.this, UserActivity.class);
                i.putExtra("user", u);
                startActivity(i, optionsCompat.toBundle());
            }
        });

        recyclerView.setAdapter(planAdapter);

        UserRequest.getUserPlansById(UserActivity.this, user.getId(), new Request.ClientResponse<ArrayList<Plan>>() {
            @Override
            public void onSuccess(ArrayList<Plan> result) {
                planAdapter.setPlansData(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(UserActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

        FriendshipRequest.areFriends(UserActivity.this, Session.user.getId(), user.getId(), new Request.ClientResponse<Friendship>() {
            @Override
            public void onSuccess(Friendship result) {
                // Checks the friendship status
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
                Toast.makeText(UserActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        }, Friendship.class);

        Picasso.get()
                .load(user.getImageURL())
                .into(userAvatar);

        TextView userName = findViewById(R.id.user_name);
        userName.setText(user.getName());

        friendStatusButton.setOnClickListener((View v) -> {
            // Action depending on the friend status
            switch (friendStatus) {
                case NONE:
                    FriendshipRequest.request(UserActivity.this, Session.user.getId(), user.getId(), new Request.ClientResponse<Friendship>() {
                        @Override
                        public void onSuccess(Friendship result) {
                            friendStatus = FriendStatus.REQUEST;
                            friendStatusButton.setText(R.string.friend_requesting);
                            showResponseButtons(false);
                            Toast.makeText(UserActivity.this, getString(R.string.friendship_request_sent), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(UserActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }, Friendship.class);
                    break;
                case FRIEND:
                    FriendshipRequest.delete(UserActivity.this, Session.user.getId(), user.getId(), new Request.ClientResponse<Friendship>() {
                        @Override
                        public void onSuccess(Friendship result) {
                            friendStatus = FriendStatus.NONE;
                            friendStatusButton.setText(R.string.friend_send_request);
                            showResponseButtons(false);
                            Toast.makeText(UserActivity.this, getString(R.string.friendship_deleted), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(UserActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }, Friendship.class);
                    break;
            }
        });

        acceptRequestButton.setOnClickListener((View v) -> {
            FriendshipRequest.accept(UserActivity.this, user.getId(), Session.user.getId(), new Request.ClientResponse<Friendship>() {
                @Override
                public void onSuccess(Friendship result) {
                    friendStatus = FriendStatus.FRIEND;
                    friendStatusButton.setText(R.string.friend_remove);
                    showResponseButtons(false);
                    Toast.makeText(UserActivity.this, getString(R.string.friendship_accepted), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(UserActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }, Friendship.class);
        });

        declineRequestButton.setOnClickListener((View v) -> {
            FriendshipRequest.decline(UserActivity.this, Session.user.getId(), user.getId(), new Request.ClientResponse<Friendship>() {
                @Override
                public void onSuccess(Friendship result) {
                    friendStatus = FriendStatus.NONE;
                    friendStatusButton.setText(R.string.friend_send_request);
                    showResponseButtons(false);
                    Toast.makeText(UserActivity.this, getString(R.string.friendship_declined), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(UserActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }, Friendship.class);
        });
    }

    private void showResponseButtons(boolean show) {
        // When the logged user has to accept/decline a request or not
        responseLayout.setEnabled(show);
        responseLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        friendStatusButton.setEnabled(!show);
        friendStatusButton.setVisibility(show ? View.GONE : View.VISIBLE);
        if (show) {
            responseLayout.bringToFront();
        } else {
            friendStatusButton.bringToFront();
        }
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
