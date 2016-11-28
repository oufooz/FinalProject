package com.example.omaro.maptest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                0);
                }
        }

        public void beginMapStart(View view) {
                Intent bms = new Intent(this,MapActivity.class);
                bms.putExtra("NICK","null");
                bms.putExtra("LAT",40.600858);
                bms.putExtra("LONG",-74.2920317);
                startActivity(bms);
        }

        public void listLocationStart(View view) {
                Intent lls = new Intent(this,ListLocation.class);
                startActivity(lls);
        }
}
