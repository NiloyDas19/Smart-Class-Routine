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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowCourses extends AppCompatActivity {

    Toolbar SCtoolbar;
    TextInputLayout SCdept,SCyear,SCsemester;
    AutoCompleteTextView SCdepttv,SCyeartv,SCsemestertv;
    Button submit;
    public static String year,semester,dept;

    public static String getDept(){
        return dept;
    }
    public static String getYear() { return year; }
    public static String getSemester(){return semester;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_courses);

        SCtoolbar = findViewById(R.id.SCtoolbar);
        setSupportActionBar(SCtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SCdept = findViewById(R.id.SCdept);
        SCyear = findViewById(R.id.SCyear);
        SCsemester = findViewById(R.id.SCsemester);
        SCdepttv = findViewById(R.id.SCdepttv);
        SCyeartv = findViewById(R.id.SCyeartv);
        SCsemestertv = findViewById(R.id.SCsemestertv);
        submit = findViewById(R.id.SCbtn);

        String[] alldepts = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science","EST","NFT","APPT","Climate and Disaster Management","PESS","Physiotherapy and Rehabilitaion","English","Physics","Chemistry","Mathematics","AIS","Managment","Finance and Bancking","Marketing"};
        String[] allyear = {"1st","2nd","3rd","4th"};
        String[] allsemester = {"1st","2nd"};

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(ShowCourses.this,R.layout.items_list,alldepts);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(ShowCourses.this,R.layout.items_list,allyear);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(ShowCourses.this,R.layout.items_list,allsemester);

        SCdepttv.setAdapter(deptAdapter);
        SCyeartv.setAdapter(yearAdapter);
        SCsemestertv.setAdapter(semesterAdapter);

        SCyeartv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearAdapter.getItem(i);
                Toast.makeText(ShowCourses.this, year, Toast.LENGTH_SHORT).show();
            }
        });

        SCsemestertv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semester = semesterAdapter.getItem(i);
                Toast.makeText(ShowCourses.this, semester, Toast.LENGTH_SHORT).show();
            }
        });

        SCdepttv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(ShowCourses.this, dept, Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowCourses.this,CourseShowingActivity.class));
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