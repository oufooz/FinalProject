package com.example.omaro.maptest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

        private BroadcastReceiver receiver;
        //private TextView test;
        private SQLhelper SQLhelper;
        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                0);
                }
                mContext = this;
                //test = (TextView) findViewById(R.id.tv_coord);
                //Start Service
                Intent service = new Intent(this, GPSService.class);
                startService(service);
        }

        @Override
        protected  void onResume(){
                super.onResume();
                if(receiver == null){
                        receiver = new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                        //RECEIVE TASK

                                }
                        };
                }
                registerReceiver(receiver,new IntentFilter("update"));
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

        public void AddTaskClick(View view) {
                //attaching a list to a location that then gets fetched when in particular location.
                // automatically detecting when near car.. and does route to home.
        }
}
