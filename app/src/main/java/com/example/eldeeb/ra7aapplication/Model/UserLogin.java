package com.example.eldeeb.ra7aapplication.Model;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLogin implements IUserLogin {
    String email, password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isValidate() {
        if (!TextUtils.isEmpty(getEmail()) && !TextUtils.isEmpty(getPassword())) {
            Log.d("check for answers", "success1");
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Log.d("check for answers", "success");

                    } else
                        Log.d("check for answers", "failed");

                }
            });
            return true;
        }

        return false;
    }


}
