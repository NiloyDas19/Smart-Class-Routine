package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class ShowCourseDistribution extends AppCompatActivity {

    Toolbar SCDtoolbar;
    TextInputLayout SCDdept,SCDyear,SCDsemester;
    AutoCompleteTextView SCDdepttv,SCDyeartv,SCDsemstertv;
    Button submit;
    public static String dept,year,semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course_distribution);

        SCDtoolbar = findViewById(R.id.SCDtoolbar);
        setSupportActionBar(SCDtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SCDdept = findViewById(R.id.SCDdept);
        SCDyear = findViewById(R.id.SCDyear);
        SCDsemester = findViewById(R.id.SCDsemester);
        SCDdepttv = findViewById(R.id.SCDdepttv);
        SCDyeartv = findViewById(R.id.SCDyeartv);
        SCDsemstertv = findViewById(R.id.SCDsemestertv);
        submit = findViewById(R.id.SCDbtn);

        String[] alldepts = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science","EST","NFT","APPT","Climate and Disaster Management","PESS","Physiotherapy and Rehabilitaion","English","Physics","Chemistry","Mathematics","AIS","Managment","Finance and Bancking","Marketing"};
        String[] allyear = {"1st","2nd","3rd","4th"};
        String[] allsemester = {"1st","2nd"};

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(ShowCourseDistribution.this,R.layout.items_list,alldepts);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(ShowCourseDistribution.this,R.layout.items_list,allyear);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(ShowCourseDistribution.this,R.layout.items_list,allsemester);

        SCDdepttv.setAdapter(deptAdapter);
        SCDyeartv.setAdapter(yearAdapter);
        SCDsemstertv.setAdapter(semesterAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dept = SCDdepttv.getText().toString();
                year = SCDyeartv.getText().toString();
                semester = SCDsemstertv.getText().toString();
                startActivity(new Intent(getApplicationContext(),CourseDistributionShowingActivity.class));
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