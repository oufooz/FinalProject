package com.example.omaro.maptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText user, pass;
    private Button create, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        create = (Button) findViewById(R.id.btn_create);
        register = (Button) findViewById(R.id.btn_login_activity);
        user = (EditText) findViewById(R.id.et_username_register);
        pass = (EditText) findViewById(R.id.et_password_register);

        create.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(create)) {
            String usernameString = user.getText().toString();
            String passwordString = pass.getText().toString();

            //SEND TO FIREBASE

            Intent startNewActivity = new Intent(this, MainActivity.class);
            startActivity(startNewActivity);
            finish();
        }
        else if (v.equals(register)){
            Log.d("TAG", "REGISTER PRESSED");
            Intent startNewActivity = new Intent(this, LoginActivity.class);
            startActivity(startNewActivity);
            finish();
        }
    }
}