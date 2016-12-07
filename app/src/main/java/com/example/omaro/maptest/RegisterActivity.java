package com.example.omaro.maptest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText user, pass;
    private Button create, register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        create = (Button) findViewById(R.id.btn_login_activity);
        register = (Button) findViewById(R.id.btn_create);
        user = (EditText) findViewById(R.id.et_username_register);
        pass = (EditText) findViewById(R.id.et_password_register);

        mAuth = FirebaseAuth.getInstance();

        create.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(create)) {
                Log.d("TAG", "Switch PRESSED");

            //SEND TO FIREBASE
            Intent startNewActivity = new Intent(this, LoginActivity.class);
            startActivity(startNewActivity);
            finish();
        }
        else if (v.equals(register)){
            Log.d("TAG", "REGISTER PRESSED");
                if(user.getText().equals("") || pass.getText().equals(""))
                {
                        Toast.makeText(this, "User And Password fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else {

                        mAuth.createUserWithEmailAndPassword(user.getText().toString(), pass.getText().toString())
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                                if (task.isSuccessful()) {
                                                        Intent startNewActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        startActivity(startNewActivity);
                                                        finish();
                                                } else {
                                                        Toast.makeText(RegisterActivity.this, "Registration UnSucessful \n" + task.getException(), Toast.LENGTH_SHORT).show();
                                                }
                                        }
                                });
                }


        }
    }
}