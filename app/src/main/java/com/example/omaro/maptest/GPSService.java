package com.example.omaro.maptest;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.abs;

public class GPSService extends Service {

    private LocationListener listener;
    private LocationManager manager;
    private SQLhelper SQLhelper;
    private double lat, lon;

    public GPSService() {
    }

    @Override
    public void onCreate(){

            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    returnTask(location);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    //Send to Settings if Disabled
                    Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                }
            };
            manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);

    }

    private void returnTask(Location current){
        SQLhelper = new SQLhelper(this);
        ArrayList<Pair<String,LatLng>> t  = SQLhelper.getEntireDataBaseMap();

        for (int i = 0; i<t.size(); i++){
            LatLng temp = t.get(i).second;
            Location loc = new Location("");
            loc.setLatitude(temp.latitude);
            loc.setLongitude(temp.longitude);
            loc.setAccuracy(50);

            if (current.distanceTo(loc) <= 200){
                String nick = t.get(i).first;
                Log.d("TAG-Service",nick + "IN RANGE");
                Intent update = new Intent("update");
                update.putExtra("nick", nick);
                sendBroadcast(update);

            }

//            if (abs(temp.latitude - lat) <= 100 && abs(temp.longitude - lon)<=100){
//                String nick = t.get(i).first;
//                Intent update = new Intent("update");
//                update.putExtra("nick", nick);
//                sendBroadcast(update);
//            }
        }


    }

//    protected void addProximityAlert(String nick, double lat, double lon){
//
//        manager.addProximityAlert(lat, lon, 100, -1, pending);
//        Intent update = new Intent("Entered");
//        PendingIntent pending = PendingIntent.getActivity(this,0,update,0);
//
//        IntentFilter filter = new IntentFilter("Entered");
//        registerReceiver(sendTask(), filter);
//
//
//    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (manager != null){
            manager.removeUpdates(listener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
