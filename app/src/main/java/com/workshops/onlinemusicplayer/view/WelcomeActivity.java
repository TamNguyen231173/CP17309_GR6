package com.workshops.onlinemusicplayer.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.workshops.onlinemusicplayer.R;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    ImageView image_welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        hideSystemBars();

        mAuth = FirebaseAuth.getInstance();

        image_welcome = findViewById(R.id.image_welcome);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        image_welcome.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent startLandingPageActivity = new Intent(WelcomeActivity.this, GetStartedActivity.class);
                startActivity(startLandingPageActivity);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, 3500);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d(TAG, "Currently Signed in: " + currentUser.getEmail());
            Toast.makeText(WelcomeActivity.this, "Currently Logged in: " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
}