package com.example.omaro.maptest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddReminderTask extends AppCompatActivity implements View.OnClickListener{

    private Button done;
    private EditText name, note;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder_task);

        done = (Button) findViewById(R.id.btn_reminder_done);
        name= (EditText) findViewById(R.id.et_reminder_name);
        note= (EditText) findViewById(R.id.et_reminder_note);
        spinner = (Spinner) findViewById(R.id.spin_dest);

        SQLhelper t = new SQLhelper(this);
        ArrayList<String> temp = t.getEntireDataBase();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,temp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        done.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if (v.equals(done)){
            String destination = spinner.getSelectedItem().toString();

            //SharedPref to add reminder
            SharedPreferences remindPref = getSharedPreferences(name.getText().toString(),MODE_PRIVATE);
            SharedPreferences.Editor remindEdit = remindPref.edit();

            remindEdit.putString("note",note.getText().toString());
            remindEdit.putString("type", "REM");
            remindEdit.commit();

            //SharedPref to add task to task list
            SharedPreferences destPref = getSharedPreferences(destination,MODE_PRIVATE);
            SharedPreferences.Editor destEdit = destPref.edit();

            Set<String> tasks = destPref.getStringSet("Tasks", new HashSet<String>());
            tasks.add(name.getText().toString());

            destEdit.putStringSet("Tasks", tasks);
            destEdit.commit();

            Log.d("TAG-REM", "REMINDER SAVED");


            finish();


        }

    }

}
