package com.example.omaro.maptest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddNavTask extends AppCompatActivity {
        private Spinner Source;
        private Spinner Destination;
        EditText ted;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_addnav_task);

                ted = (EditText) findViewById(R.id.inputTaskName);

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
                String taskname = ted.getText().toString();

                SharedPreferences thisone = getSharedPreferences(taskname,MODE_PRIVATE);
                SharedPreferences.Editor thisoneeditor = thisone.edit();

                thisoneeditor.putString("type","NAV");
                thisoneeditor.putString("dest",k);

                thisoneeditor.apply();

                SharedPreferences temp = getSharedPreferences(t,MODE_PRIVATE);
                SharedPreferences.Editor tempeditor = temp.edit();

                Set<String> tasks  = temp.getStringSet("Tasks",new HashSet<String>());

                tasks.add(taskname);

                tempeditor.putStringSet("Tasks",tasks);
                tempeditor.apply();

                finish();
        }
}
