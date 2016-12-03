package com.example.omaro.maptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener{

    private Button temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        temp  = (Button) findViewById(R.id.btn_temp);
        temp.setOnClickListener(this);


    }

    //TODO: Implement SharedPref checking

    @Override
    public void onClick(View v) {
        if (v.equals(temp)) {
            Intent startNewActivity = new Intent(this, LoginActivity.class);
            startActivity(startNewActivity);
            finish();
        }
    }
}
