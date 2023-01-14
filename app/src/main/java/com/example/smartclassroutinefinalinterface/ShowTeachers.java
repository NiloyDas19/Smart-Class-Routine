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

public class ShowTeachers extends AppCompatActivity {

    Toolbar STtoolbar;
    TextInputLayout STdept;
    AutoCompleteTextView STdepttv;
    Button submit;
    public static String dept;

    public static String getDept(){
        return dept;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teachers);

        STtoolbar = findViewById(R.id.STtoolbar);
        setSupportActionBar(STtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        STdept = findViewById(R.id.STdept);
        STdepttv  = findViewById(R.id.STdepttv);
        submit = findViewById(R.id.STbtn);

        String[] alldepts = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science","EST","NFT","APPT","Climate and Disaster Management","PESS","Physiotherapy and Rehabilitaion","English","Physics","Chemistry","Mathematics","AIS","Managment","Finance and Bancking","Marketing"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(ShowTeachers.this,R.layout.items_list,alldepts);
        STdepttv.setAdapter(deptAdapter);

        STdepttv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(ShowTeachers.this, dept, Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowTeachers.this, TeacherShowingActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}