package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class BeforeRoutine extends AppCompatActivity {

    Toolbar BRtoolbar;
    TextInputLayout BRsemester;
    AutoCompleteTextView BRsemestertv;
    Button BRbtn;
    public static String semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_routine);

        BRtoolbar = findViewById(R.id.BRtoolbar);
        BRsemester = findViewById(R.id.BRsemester);
        BRsemestertv = findViewById(R.id.BRsemestertv);
        BRbtn = findViewById(R.id.BRbtn);

        setSupportActionBar(BRtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String[] allsemester = {"1st","2nd"};
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(BeforeRoutine.this,R.layout.items_list,allsemester);

        BRsemestertv.setAdapter(semesterAdapter);

        BRsemestertv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semester = semesterAdapter.getItem(i);
                Toast.makeText(BeforeRoutine.this, semester, Toast.LENGTH_SHORT).show();
            }
        });

        BRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeforeRoutine.this,TableActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}