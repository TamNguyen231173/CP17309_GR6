package com.workshops.onlinemusicplayer.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.CallbackManager;

import com.workshops.onlinemusicplayer.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private static final String TAG_GG = "GoogleActivity";
    private static final String TAG_FB = "FacebookLogin";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private CallbackManager mCallbackManager;

    EditText edt_email, edt_password;
    Button btn_login;
    SignInButton btn_login_gg;
    TextView btn_move_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        edt_email = findViewById(R.id.edt_login_email);
        edt_password = findViewById(R.id.edt_login_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login_gg = findViewById(R.id.btn_login_gg);
        btn_move_register = findViewById(R.id.btn_move_register);


        btn_login.setOnClickListener(view -> {
            loginUser();
        });

        btn_move_register.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        btn_login_gg.setOnClickListener(view -> {
            signIn();
        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.btn_login_fb);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG_FB, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG_FB, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG_FB, "facebook:onError", error);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d(TAG, "Currently Signed in: " + currentUser.getEmail());
            Toast.makeText(LoginActivity.this, "Currently Logged in: " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    public void CallBack(View view){
        Intent intent = new Intent(LoginActivity.this, LoginRegisterActivity.class);
        startActivity(intent);
    }


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, UI will update with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication Succeeded.", Toast.LENGTH_SHORT).show();
                            updateUIFB(user);
                        } else {
                            // If sign-in fails, a message will display to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUIFB(null);
                        }
                    }
                });
    }
    // [END auth_with_facebook]

    private void updateUIFB(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG_GG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG_GG, "Google sign in failed", e);
            }
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG_GG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG_GG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));

        } else {
            Toast.makeText(this, "You Didnt signed in", Toast.LENGTH_LONG).show();
        }
    }

    private void loginUser() {
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();

        //Validate if edit empty
        if (TextUtils.isEmpty(email)) {
            edt_email.setError("Name cannot be empty");
        } else if (TextUtils.isEmpty(password)) {
            edt_password.setError("Email cannot be empty");
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Log in error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}