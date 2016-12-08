package com.example.omaro.maptest;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;



public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
        GoogleMap mMap;
        private TextView text ;
        private int count = 0;
        private LatLng originonstartup = null;
        private SQLhelper sqLhelper;
        public ArrayList<Pair<String,LatLng>> locations = new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_map);
                text = (TextView) findViewById(R.id.texty);

                // Getting Input
                Intent temp = getIntent();
                originonstartup = new LatLng(temp.getDoubleExtra("LAT",0.0),temp.getDoubleExtra("LONG",0.0));


                // Initializing Map
                MapFragment mMapFragment = MapFragment.newInstance();
                FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.map, mMapFragment);
                fragmentTransaction.commit();
                mMapFragment.getMapAsync(this);
                sqLhelper = new SQLhelper(this);
                Toast.makeText(this, "Opened", Toast.LENGTH_SHORT).show();




        }

        @Override
        public void onMapReady(GoogleMap map) {
                mMap = map;
                try {
                        map.setMyLocationEnabled(true);
                }
                catch(SecurityException e)
                {
                        e.printStackTrace();
                }
                mMap.setMinZoomPreference(12.0f);
                // Sett bounds later on
                //mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(-124.848974,49.384358),new LatLng(-66.885444,24.396308)));
                if(count == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(originonstartup).build();
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        Loader load = new Loader(this);
                        load.execute(locations);

                }
       }
        public void updateMap()
        {
                for(int i =0 ;i < locations.size();i++)
                {
                        Log.d("Locations working",locations.get(i).first );
                        mMap.addMarker(new MarkerOptions().position(locations.get(i).second).title(locations.get(i).first));
                }
        }

        public void test(View view) {
                MapFragment mapFragment = (MapFragment) getFragmentManager()
                        .findFragmentById(R.id.map);
                count++;
                Random randy = new Random();
                LatLng t = mMap.getCameraPosition().target;
                String u = t.longitude + " " + t.latitude;
                text.setText(u);
                mMap.addMarker(new MarkerOptions().position(t).title("Last" + count));
                sqLhelper.insertEntry("Last"+ count, t.latitude,t.longitude);
                mapFragment.getMapAsync(this);
        }
}
class Loader extends AsyncTask<ArrayList<Pair<String,LatLng>>,Integer,Long> {

        private Context mContext;
        public Loader(Context context)
        {
                mContext = context;
        }

        @Override
        protected Long doInBackground(ArrayList<Pair<String, LatLng>>... params) {
                ArrayList<Pair<String, LatLng>> location = params[0];
                SQLhelper loaderhelper = new SQLhelper(mContext);
                ArrayList<String> t = loaderhelper.getEntireDataBase();
                for(int i  = 0 ; i < t.size();i++)
                {
                        LatLng tempy = loaderhelper.getEntryByNickLatLong(t.get(i));
                        location.add(new Pair<String,LatLng> (t.get(i),tempy));
                }
                return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
              MapActivity temp  = (MapActivity) mContext;
                temp.updateMap();
        }
}
