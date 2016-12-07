package com.example.omaro.maptest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class GPSService extends Service {

    private LocationManager manager;
    private SQLhelper SQLhelper;

    public GPSService() {
    }

    @Override
    public void onCreate(){

            manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

    }

    private void returnTask(){
        SQLhelper = new SQLhelper(this);
        Map<String,LatLng> t  = SQLhelper.getEntireDataBaseMap();
        for (int i = 0; i<t.size(); i++){

            //manager.addProximityAlert();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
