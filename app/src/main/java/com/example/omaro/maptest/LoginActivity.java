package com.example.omaro.maptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login, register;
    private EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        user = (EditText) findViewById(R.id.et_username);
        pass = (EditText) findViewById(R.id.et_password);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(login)) {

            Log.d("TAG", "LOGIN PRESSED");
            String usernameString = user.getText().toString();
            String passwordString = pass.getText().toString();

            //CHECK FIREBASE

            Intent startNewActivity = new Intent(this, MainActivity.class);
            startActivity(startNewActivity);
            finish();

        }

        else if (v.equals(register)){
            Log.d("TAG", "REGISTER PRESSED");
            Intent startNewActivity = new Intent(this, RegisterActivity.class);
            startActivity(startNewActivity);
            finish();
        }
    }
}
