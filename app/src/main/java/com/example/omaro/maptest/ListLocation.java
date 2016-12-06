package com.example.omaro.maptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ListLocation extends AppCompatActivity {
        private SQLhelper sqLhelper;
        private static int MODIFY_ENTRY_REQ = 10;
        private ListView listy;
        private boolean resumingwithresult = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_list_location);
                listy = (ListView) findViewById(R.id.ListViewListLocation);
                sqLhelper = new SQLhelper(this);
                ArrayList<String> t  = sqLhelper.getEntireDataBase();
                final ArrayAdapter dapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,t);
                listy.setAdapter(dapter);
                listy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String t = listy.getItemAtPosition(position).toString();
                                Intent temp = new Intent(ListLocation.this,MapActivity.class);
                                LatLng tedy = sqLhelper.getEntryByNickLatLong(t);
                                temp.putExtra("NICK",t);
                                temp.putExtra("LONG",tedy.longitude);
                                temp.putExtra("LAT",tedy.latitude);
                                startActivity(temp);
                        }
                });
                listy.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.d("LongClick", "True");
                                String t = listy.getItemAtPosition(position).toString();

                                int i =  sqLhelper.getIdByNick(t);
                                /*
                                LatLng tedy = sqLhelper.getEntryByNickLatLong(t);
                                sqLhelper.updateEntry(i,"TOM",tedy.latitude,tedy.longitude);*/

                                Bundle args = new Bundle();
                                args.putInt("ID" , i);
                                args.putString("NICK",t);
/*                                args.putParcelable("LatLong",tedy);
                                args.putString("nick",t);*/
                                Intent intent = new Intent(ListLocation.this,modifyContact.class);
                                intent.putExtras(args);
                                startActivityForResult(intent,1);
                                return true;
                        }
                });
        }

        @Override
        protected void onPostResume() {
                super.onPostResume();
                ArrayList<String> t  = sqLhelper.getEntireDataBase();
                final ArrayAdapter dapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,t);
                listy.setAdapter(dapter);
        }
}
