package com.example.omaro.maptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class modifyContact extends AppCompatActivity {
        private int ID ;
        private SQLhelper sqLhelper;
        private TextView oldname;
        private TextView LatLong;
        private EditText newname;
        private LatLng latLng;
        private String OldNick;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_modify_contact);

                oldname = (TextView) findViewById(R.id.PreviousName);
                LatLong = (TextView) findViewById(R.id.LatLong);
                newname = (EditText) findViewById(R.id.NewName);

                sqLhelper = new SQLhelper(this);

                Intent intent = getIntent();
                ID = intent.getIntExtra("ID", 0);
                OldNick = intent.getStringExtra("NICK");

                latLng = sqLhelper.getEntryByNickLatLong(OldNick);

                oldname.setText(OldNick);
                LatLong.setText(latLng.latitude + " " + latLng.longitude);
        }

        public void Done(View view) {
                sqLhelper.updateEntry(OldNick, newname.getText().toString(),latLng.latitude,latLng.longitude);
                setResult(1);
                finish();
        }

        public void Delete(View view) {
                sqLhelper.deleteEntryByNick(oldname.getText().toString());
                setResult(1);
                finish();
        }
}
