package com.example.eldeeb.ra7aapplication.Model;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegister implements IUserRegister {
    String fullName, email, password;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public UserRegister(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    public String getFullName() {
        return fullName;
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
        if (!TextUtils.isEmpty(getFullName())
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && getPassword().length() > 6){
            mAuth.createUserWithEmailAndPassword(getEmail(),getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference Current_User_DB = mDatabase.child(user_id);
                        Current_User_DB.child("name").setValue(getFullName());

                    }
                }
            });

            return true;
        }
        else
            return false;
    }
}
