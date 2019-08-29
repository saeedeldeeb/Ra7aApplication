package com.example.eldeeb.ra7aapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eldeeb.ra7aapplication.MapsActivity;
import com.example.eldeeb.ra7aapplication.Presenter.ILoginPresenter;
import com.example.eldeeb.ra7aapplication.Presenter.loginPresenter;
import com.example.eldeeb.ra7aapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginUI extends AppCompatActivity implements ILoginView {

    private static final String TAG ="LoginUIClass" ;
    EditText email, password;
    Button login_btn;
    SignInButton mGoogleSignIn;

    ILoginPresenter loginPresenter;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);
        mAuth = FirebaseAuth.getInstance();

        //initView
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.pass_login);
        login_btn = findViewById(R.id.login_btn);
        mGoogleSignIn = findViewById(R.id.googleSignBtn);

        loginPresenter = new loginPresenter(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
loginPresenter.OnLogin(email.getText().toString().trim()
        ,password.getText().toString().trim());
            }
        });

        mGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_credentials))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }


    @Override
    public void OnLoginResult(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(LoginUI.this, MapsActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Google sign in failed", e);
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Intent mainIntent = new Intent(LoginUI.this, MapsActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }
}
