package com.example.eldeeb.ra7aapplication.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eldeeb.ra7aapplication.MapsActivity;
import com.example.eldeeb.ra7aapplication.Presenter.IRegisterPresenter;
import com.example.eldeeb.ra7aapplication.Presenter.registerPresenter;
import com.example.eldeeb.ra7aapplication.R;


public class RegisterationUI extends AppCompatActivity implements IRegiserView {

    EditText email, password, fullname;
    Button register_btn,logindirectbtn;

    IRegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_ui);

        //initView
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fullname=findViewById(R.id.fullname);
        register_btn=findViewById(R.id.btnRegister);
        logindirectbtn=findViewById(R.id.btnLinkToLoginScreen);

        registerPresenter = new registerPresenter(this);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPresenter.onRegister(fullname.getText().toString().trim()
                        ,email.getText().toString().trim()
                        ,password.getText().toString().trim());
            }
        });

        logindirectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterationUI.this,LoginUI.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });
    }


    @Override
    public void onRegiserResult(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(RegisterationUI.this, MapsActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }
}
