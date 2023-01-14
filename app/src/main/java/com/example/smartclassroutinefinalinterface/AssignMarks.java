package com.example.smartclassroutinefinalinterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignMarks extends AppCompatActivity {

    Toolbar toolbar;
    TextInputLayout yearLayout, semesterLayout, courseCodeLayout, assignMarksTypeLayout, assignMarksTypeNumberLayout,deptLayout;
    AutoCompleteTextView yearTextView,semesterTextView,courseCodeTextView,assignMarksTypeTextView,assignMarksTypeNumberTextView,deptTextView;
    Button assignMarksButton;
    ArrayAdapter<String> courseAdapter;
    ArrayList<String > list = new ArrayList<>();
    DatabaseReference user;
    String currentUser;

    static String year,semester,courseCode,type,number,dept,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_marks);

        toolbar = findViewById(R.id.AssignMarksToolbar);
        yearLayout = findViewById(R.id.AssignMarksYear);
        semesterLayout = findViewById(R.id.AssignMarksSemester);
        courseCodeLayout = findViewById(R.id.AssignMarksCourseCode);
        assignMarksTypeLayout = findViewById(R.id.AssignMarksType);
        assignMarksTypeNumberLayout = findViewById(R.id.AssignMarksTypeNumber);
        deptLayout = findViewById(R.id.assignMarksDept);
        assignMarksButton = findViewById(R.id.btnAssignMarks);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

        yearTextView = findViewById(R.id.AssignMarksYearTextView);
        semesterTextView = findViewById(R.id.AssignMarksSemesterTextView);
        courseCodeTextView = findViewById(R.id.AssignMarksCourseCodeTextView);
        assignMarksTypeTextView = findViewById(R.id.AssignMarksTypeTextView);
        assignMarksTypeNumberTextView = findViewById(R.id.AssignMarksTypeNumberTextView);
        deptTextView = findViewById(R.id.assignMarksDeptTextView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String[] allDept = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(AssignMarks.this,R.layout.items_list,allDept);
        deptTextView.setAdapter(deptAdapter);
        String[] allyear = {"1st","2nd","3rd","4th"};
        String[] allsemester = {"1st","2nd"};
        String[] allType = {"CT","Assignment","Presentation","Lab","Viva"};
        String[] allTypeNumbers = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(AssignMarks.this,R.layout.items_list,allyear);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(AssignMarks.this,R.layout.items_list,allsemester);
        ArrayAdapter<String> allTypeAdapter = new ArrayAdapter<>(AssignMarks.this,R.layout.items_list,allType);
        ArrayAdapter<String> allTypeNumbersAdapter = new ArrayAdapter<>(AssignMarks.this,R.layout.items_list,allTypeNumbers);


        yearTextView.setAdapter(yearAdapter);
        semesterTextView.setAdapter(semesterAdapter);
        assignMarksTypeTextView.setAdapter(allTypeAdapter);
        assignMarksTypeNumberTextView.setAdapter(allTypeNumbersAdapter);

        yearTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearAdapter.getItem(i);
                Toast.makeText(AssignMarks.this, year, Toast.LENGTH_SHORT).show();
            }
        });

        semesterTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semester = semesterAdapter.getItem(i);
                Toast.makeText(AssignMarks.this, semester, Toast.LENGTH_SHORT).show();
            }
        });

        deptTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(AssignMarks.this, deptAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        courseCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Teachers teachers = new Teachers();
                        teachers = snapshot.getValue(Teachers.class);
                        id = teachers.getId();
                        Log.i("aaa",id);
                        courseAdapterHelper(id);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        assignMarksTypeTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                type = allTypeAdapter.getItem(i);
                Toast.makeText(AssignMarks.this, type, Toast.LENGTH_SHORT).show();
            }
        });

        assignMarksTypeNumberTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                number = allTypeNumbersAdapter.getItem(i);
                Toast.makeText(AssignMarks.this, number, Toast.LENGTH_SHORT).show();
            }
        });

        assignMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseCode = courseCodeTextView.getText().toString();
                Intent intent = new Intent(AssignMarks.this,Assign_Marks_ListView.class);
                startActivity(intent);
            }
        });


    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void courseAdapterHelper(String id){
        dept = deptTextView.getText().toString();
        year = yearTextView.getText().toString();
        semester = semesterTextView.getText().toString();

        if (year != null && semester != null && dept != null && id != null){
            DatabaseReference courseFirebase = FirebaseDatabase.getInstance().getReference().child("CourseDistribution").child(dept).child(year).child(semester);
            courseFirebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        DistributionClass distributionClass = dataSnapshot.getValue(DistributionClass.class);
                        Log.i("aaa",distributionClass.getTeacherId()+id);
                        if (distributionClass.getTeacherId().equals(id))
                            list.add(distributionClass.getCourseCode());
                    }
                    int len = list.size(),i=0;
                    String[] allCourses = new String[len];
                    for (String s:list){
                        allCourses[i] = s;
                        i++;
                    }
                    courseAdapter = new ArrayAdapter<>(AssignMarks.this,R.layout.items_list,allCourses);
                    courseCodeTextView.setAdapter(courseAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}