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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class GenerateRoutine extends AppCompatActivity {

    Toolbar BRtoolbar;
    TextInputLayout BRyear,BRsemester;
    AutoCompleteTextView BRyeartv,BRsemestertv;
    Button BRbtn;
    String year,semester;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference courseTable,routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_routine);

        BRtoolbar = findViewById(R.id.BRtoolbar);
        BRyear = findViewById(R.id.BRyear);
        BRsemester = findViewById(R.id.BRsemester);
        BRyeartv = findViewById(R.id.BRyeartv);
        BRsemestertv = findViewById(R.id.BRsemestertv);
        BRbtn = findViewById(R.id.BRbtn);

        setSupportActionBar(BRtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String[] allyear = {"1st","2nd","3rd","4th"};
        String[] allsemester = {"1st","2nd"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(GenerateRoutine.this,R.layout.items_list,allyear);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(GenerateRoutine.this,R.layout.items_list,allsemester);

        BRyeartv.setAdapter(yearAdapter);
        BRsemestertv.setAdapter(semesterAdapter);

        BRyeartv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearAdapter.getItem(i);
                Toast.makeText(GenerateRoutine.this, year, Toast.LENGTH_SHORT).show();
            }
        });

        BRsemestertv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semester = semesterAdapter.getItem(i);
                Toast.makeText(GenerateRoutine.this, semester, Toast.LENGTH_SHORT).show();
            }
        });

        BRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            courseTable = firebaseDatabase.getReference().child("Courses").child("CSE").child(year).child(semester);
                courseTable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String [][] table = new String[6][9];
                        String [][] courseInformation = new String[15][3];
                        int len=0;
                        int i=1,j=0,k=0;

                        //Retrieving course information from database
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            Course course = dataSnapshot1.getValue(Course.class);

                            courseInformation[len][0] = course.getCode();
                            courseInformation[len][1] = course.getCredit();
                            courseInformation[len][2] = course.getType();

                            len++;
                        }

                        //Initializing the routine table
                        for (i=1;i<6;i++){
                            for (j=1;j<9;j++){
                                table[i][j] = "NoClass";
                            }
                        }

                        //Routine automation
                        for (i = 0; i < len; i++) {
                            String course = courseInformation[i][0];
                            String creditString = courseInformation[i][1];
                            String courseType = courseInformation[i][2];
                            double credit = Double.valueOf(creditString);
                            if (courseType.equals("Lab")) credit *= 2;
                            for (j = 1; j < 6 && credit > 0; j++) {
                                Boolean isAvilable = true;
                                int pos = -1;
                                for (k = 1; k < 9; k++) {
                                    if (k == 5) continue;
                                    if (table[j][k].equals("NoClass")) {
                                        pos = k;
                                    } else if (table[j][k].equals(course)) {
                                        isAvilable = false;
                                    }
                                }
                                if (pos != -1 && isAvilable) {
                                    table[j][pos] = course;
                                    credit -= 1.0;
                                }
                            }
                        }

                        //Store routine on database
                        routine = firebaseDatabase.getReference().child("Routine").child("CSE").child(year).child(semester);
                        for (i=1;i<6;i++){
                            for (j=1;j<9;j++){
                                String day = getDay(i);
                                String time = getTime(j);
                                routine.child(day).child(time).setValue(table[i][j]);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(GenerateRoutine.this, "Routine Generated", Toast.LENGTH_SHORT).show();
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

    public String getDay(int i){
        String day = "Fri";
        if (i==1)           day = "Sat";
        else if (i==2)      day = "Sun";
        else if (i==3)      day = "Mon";
        else if (i==4)      day = "Tue";
        else if (i==5)      day = "Wed";

        return day;
    }

    public String getTime(int j){
        String time = "0820";
        if (j==1)           time = "0920";
        else if (j==2)      time = "1020";
        else if (j==3)      time = "1120";
        else if (j==4)      time = "1220";
        else if (j==5)      time = "0120";
        else if (j==6)      time = "0220";
        else if (j==7)      time = "0320";
        else if (j==8)      time = "0420";

        return  time;
    }

}