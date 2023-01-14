package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseDistribution extends AppCompatActivity {
    ArrayAdapter<String> teacherAdapter;
    ArrayAdapter<String> courseAdapter;

    Toolbar CDtoolbar;
    TextInputLayout CDdept,CDyear,CDsemester;
    AutoCompleteTextView CDdepttv,CDyeartv,CDsemstertv;
    TextInputLayout CDtacherId,CDcourseCode;
    AutoCompleteTextView CDteachersidtv,CDcourscodetv;
    Button submit;
    String dept,year,semester,code,id;
    ArrayList<String > list = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_distribution);

        CDtoolbar = findViewById(R.id.CDtoolbar);
        setSupportActionBar(CDtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CDdept = findViewById(R.id.CDdept);
        CDyear = findViewById(R.id.CDyear);
        CDsemester = findViewById(R.id.CDsemester);
        CDtacherId = findViewById(R.id.CDTeacher);
        CDcourseCode = findViewById(R.id.CDCourseCode);
        CDdepttv = findViewById(R.id.CDdepttv);
        CDyeartv = findViewById(R.id.CDyeartv);
        CDsemstertv = findViewById(R.id.CDsemestertv);
        CDteachersidtv = findViewById(R.id.CDTeachertv);
        CDcourscodetv = findViewById(R.id.CDCourseCodetv);

        String[] alldepts = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science","EST","NFT","APPT","Climate and Disaster Management","PESS","Physiotherapy and Rehabilitaion","English","Physics","Chemistry","Mathematics","AIS","Managment","Finance and Bancking","Marketing"};
        String[] allyear = {"1st","2nd","3rd","4th"};
        String[] allsemester = {"1st","2nd"};

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(CourseDistribution.this,R.layout.items_list,alldepts);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(CourseDistribution.this,R.layout.items_list,allyear);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(CourseDistribution.this,R.layout.items_list,allsemester);

        CDdepttv.setAdapter(deptAdapter);
        CDyeartv.setAdapter(yearAdapter);
        CDsemstertv.setAdapter(semesterAdapter);

        CDdepttv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(CourseDistribution.this, dept, Toast.LENGTH_SHORT).show();
            }
        });

        CDyeartv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearAdapter.getItem(i);
                Toast.makeText(CourseDistribution.this, year, Toast.LENGTH_SHORT).show();
            }
        });

        CDsemstertv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semester = semesterAdapter.getItem(i);
                Toast.makeText(CourseDistribution.this,semester, Toast.LENGTH_SHORT).show();
            }
        });

        CDteachersidtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d = CDdepttv.getText().toString();
                DatabaseReference teacherFirebase = FirebaseDatabase.getInstance().getReference().child("Teachers").child(d);
                teacherFirebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            Teachers teachers = dataSnapshot.getValue(Teachers.class);
                            list.add(teachers.id);
                        }
                        int len = list.size(),i=0;
                        String[] allTeachers = new String[len];
                        for (String s:list){
                            allTeachers[i] = s;
                            i++;
                        }
                        teacherAdapter = new ArrayAdapter<>(CourseDistribution.this,R.layout.items_list,allTeachers);
                        CDteachersidtv.setAdapter(teacherAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        CDcourscodetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d = CDdepttv.getText().toString();
                String y = CDyeartv.getText().toString();
                String s = CDsemstertv.getText().toString();
                DatabaseReference courseFirebase = FirebaseDatabase.getInstance().getReference().child("Courses").child(d).child(y).child(s);
                courseFirebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            Course course = dataSnapshot.getValue(Course.class);
                            list.add(course.getCode());
                        }
                        int len = list.size(),i=0;
                        String[] allCourses = new String[len];
                        for (String s:list){
                            allCourses[i] = s;
                            i++;
                        }
                        courseAdapter = new ArrayAdapter<>(CourseDistribution.this,R.layout.items_list,allCourses);
                        CDcourscodetv.setAdapter(courseAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


//        String teacherId;
//        CDteachersidtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                teacherId = .getItem(i);
//                Toast.makeText(CourseDistribution.this, dept, Toast.LENGTH_SHORT).show();
//            }
//        });

        submit = findViewById(R.id.CDbtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        root = firebaseDatabase.getReference().child("CourseDistribution");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = CDteachersidtv.getText().toString();
                code = CDcourscodetv.getText().toString();

                findCredit();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void findCredit(){
        DatabaseReference course = FirebaseDatabase.getInstance().getReference().child("Courses").child(dept).child(year).child(semester).child(code);
        course.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String credit = snapshot.child("credit").getValue().toString();
                String type = snapshot.child("type").getValue().toString();
                String title = snapshot.child("title").getValue().toString();
                upToDatabase(title,credit,type);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void upToDatabase(String title,String credit,String type){
        //HashMap<String , String> courseDistributionTable = new HashMap<>();
//        courseDistributionTable.put("Selected Teacher ID",id);
//        courseDistributionTable.put("Course Code",code);
//        courseDistributionTable.put("credit",credit);
//        courseDistributionTable.put("type",type);
        DistributionClass distributionClass = new DistributionClass(code,id,type,credit,year);
        root.child(dept).child(year).child(semester).push().setValue(distributionClass);
        Toast.makeText(CourseDistribution.this, "Course Distributed Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(CourseDistribution.this,AdminPannel.class));
    }
}