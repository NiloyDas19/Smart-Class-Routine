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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddCourse extends AppCompatActivity {

    Toolbar ACtoolbar;
    TextInputLayout textInputLayoutDept,textInputLayoutYear,textInputLayoutSemester,textInputLayoutCourseType;
    AutoCompleteTextView autoCompleteTextViewDept,autoCompleteTextViewYear,autoCompleteTextViewSemester,autoCompleteTextViewCourseType;
    EditText courseCode,courseTitle,courseCredit;
    Button submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference root;
    String dept,year,semester,courseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        ACtoolbar = findViewById(R.id.ACtoolbar);

        setSupportActionBar(ACtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        textInputLayoutDept = findViewById(R.id.ACdept);
        textInputLayoutYear = findViewById(R.id.ACyear);
        textInputLayoutSemester = findViewById(R.id.ACsemester);
        textInputLayoutCourseType = findViewById(R.id.ACtype);

        autoCompleteTextViewDept = findViewById(R.id.ACdepttv);
        autoCompleteTextViewYear = findViewById(R.id.ACyeartv);
        autoCompleteTextViewSemester = findViewById(R.id.ACsemestertv);
        autoCompleteTextViewCourseType = findViewById(R.id.ACtypetv);

        String[] allDept = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science"};
        String[] allYear = {"1st","2nd","3rd","4th"};
        String[] allSession = {"1st","2nd"};
        String[] allCourseType = {"Theory","Lab","Viva Voce"};

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(AddCourse.this,R.layout.items_list,allDept);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(AddCourse.this,R.layout.items_list1,allYear);
        ArrayAdapter<String> sessionAdapter = new ArrayAdapter<>(AddCourse.this,R.layout.items_list2,allSession);
        ArrayAdapter<String> courseTypeAdapter = new ArrayAdapter<>(AddCourse.this,R.layout.items_list1,allCourseType);
        autoCompleteTextViewDept.setAdapter(deptAdapter);
        autoCompleteTextViewYear.setAdapter(yearAdapter);
        autoCompleteTextViewSemester.setAdapter(sessionAdapter);
        autoCompleteTextViewCourseType.setAdapter(courseTypeAdapter);

        autoCompleteTextViewDept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(AddCourse.this, deptAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextViewYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearAdapter.getItem(i);
                Toast.makeText(AddCourse.this, year, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextViewSemester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semester = sessionAdapter.getItem(i);
                Toast.makeText(AddCourse.this,semester, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextViewCourseType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                courseType = courseTypeAdapter.getItem(i);
                Toast.makeText(AddCourse.this,courseType, Toast.LENGTH_SHORT).show();
            }
        });


        courseTitle = findViewById(R.id.ACtitle);
        courseCode = findViewById(R.id.ACcode);
        courseCredit = findViewById(R.id.ACcredit);
        submit = findViewById(R.id.AClogin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        root = firebaseDatabase.getReference().child("Courses");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = courseTitle.getText().toString();
                String code = courseCode.getText().toString();
                String credit = courseCredit.getText().toString();

                Course course = new Course(title,code,credit,courseType,dept,year,semester);
                root.child(dept).child(year).child(semester).child(code).setValue(course);

                Toast.makeText(AddCourse.this,"Course added",Toast.LENGTH_SHORT).show();
                startActivity( new Intent(AddCourse.this,AdminPannel.class));
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