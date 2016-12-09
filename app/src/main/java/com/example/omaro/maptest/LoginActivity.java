package com.example.omaro.maptest;

import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

        private Button login, register;
        private EditText Email, pass;
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);

                login = (Button) findViewById(R.id.btn_login);
                register = (Button) findViewById(R.id.btn_register);
                Email = (EditText) findViewById(R.id.et_email);
                pass = (EditText) findViewById(R.id.et_password);

                mAuth = FirebaseAuth.getInstance();

                register.setOnClickListener(this);
                login.setOnClickListener(this);
                if(checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                                0);
                }

                mAuthListener = new FirebaseAuth.AuthStateListener(){
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                        if(user != null)
                                        {
                                                Log.d("Success", "DONE");
                                                Toast.makeText(LoginActivity.this, "LOGGED IN ", Toast.LENGTH_SHORT).show();
                                                Intent startNewActivity = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(startNewActivity);
                                                finish();
                                        }
                                        else
                                        {
                                                Log.d("Success", "FAIL");
                                               // Toast.makeText(LoginActivity.this, "NOT LOGGGED IN ", Toast.LENGTH_SHORT).show();
                                                // fetch data .. from online database.
                                        }
                                }
                };
         }
        @Override
        public void onStart() {
                super.onStart();
                mAuth.addAuthStateListener(mAuthListener);
        }

        @Override
        public void onStop() {
                super.onStop();
                if (mAuthListener != null) {
                        mAuth.removeAuthStateListener(mAuthListener);
                }
        }
    @Override
    public void onClick(View v) {
        if (v.equals(login)) {

                Log.d("TAG", "LOGIN PRESSED");
                String usernameString = Email.getText().toString();
                String passwordString = pass.getText().toString();
                if (usernameString.equals("") || passwordString.equals(""))
                {
                        Toast.makeText(this, "UserName or Password Is EMPTY \n Please Write a Proper Entry", Toast.LENGTH_SHORT).show();
                }
                else{
                        mAuth.signInWithEmailAndPassword(usernameString, passwordString)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (!task.isSuccessful()) {
                                                        Log.w("TAG", "signInWithEmail:failed", task.getException());
                                                        Toast.makeText(LoginActivity.this, "SignInFailed" + task.getException(),
                                                                Toast.LENGTH_SHORT).show();
                                                }
                                        }
                                });
                }


        }

        else if (v.equals(register)){
            Log.d("TAG", "REGISTER PRESSED");
            Intent startNewActivity = new Intent(this, RegisterActivity.class);
            startActivity(startNewActivity);
            finish();
        }
    }
}
