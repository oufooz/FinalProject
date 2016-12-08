package com.example.omaro.maptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddNavTask extends AppCompatActivity {
        private Spinner Source;
        private Spinner Destination;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_addNav_task);

                SQLhelper t = new SQLhelper(this);
                ArrayList<String> temp = t.getEntireDataBase();

                Source = (Spinner) findViewById(R.id.DropDownListSource) ;
                Destination = (Spinner) findViewById(R.id.DropDownListDestination) ;

                ArrayAdapter<String> dapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,temp);
                dapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Source.setAdapter(dapter);
                Destination.setAdapter(dapter);


        }


        public void DoneTask(View view) {
                String t = Source.getSelectedItem().toString();
                String k = Destination.getSelectedItem().toString();

        }
}
