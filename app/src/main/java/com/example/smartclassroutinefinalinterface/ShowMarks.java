package com.example.smartclassroutinefinalinterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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

public class ShowMarks extends AppCompatActivity {
    Toolbar toolbar;
    TextInputLayout courseCode,showMarksType,showMarksTypeNumber;
    AutoCompleteTextView showCodeTextView,showMarksTypeTextView,showMarksTypeNumberTextView;
    Button showMarksButton;

    ArrayList<String > list = new ArrayList<>();
    ArrayAdapter<String> courseCodeAdapter;

    String dept,year,semester;
    static String code,type,number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_marks);

        findDeptYearSemester();

        toolbar = findViewById(R.id.ShowMarksToolbar);
        courseCode = findViewById(R.id.ShowMarksCourseCode);
        showMarksType = findViewById(R.id.ShowMarksType);
        showMarksTypeNumber = findViewById(R.id.ShowMarksTypeNumber);
        showMarksButton = findViewById(R.id.btnShowMarks);

        showCodeTextView = findViewById(R.id.ShowMarksCourseCodeTextView);
        showMarksTypeTextView = findViewById(R.id.ShowMarksTypeTextView);
        showMarksTypeNumberTextView = findViewById(R.id.ShowMarksTypeNumberTextView);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String[] allType = {"CT","Assignment","Presentation","Lab","Viva"};
        String[] allTypeNumbers = {"1","2","3","4","5","6","7","8","9","10"};

        ArrayAdapter<String> allTypeAdapter = new ArrayAdapter<>(ShowMarks.this,R.layout.items_list,allType);
        ArrayAdapter<String> allTypeNumbersAdapter = new ArrayAdapter<>(ShowMarks.this,R.layout.items_list,allTypeNumbers);

        showMarksTypeTextView.setAdapter(allTypeAdapter);
        showMarksTypeNumberTextView.setAdapter(allTypeNumbersAdapter);

        showMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = showCodeTextView.getText().toString();
                type = showMarksTypeTextView.getText().toString();
                number = showMarksTypeNumberTextView.getText().toString();
                Intent intent = new Intent(ShowMarks.this,ShowMarks2.class);
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

    public void findDeptYearSemester(){
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dept = snapshot.child("dept").getValue().toString();
                year = snapshot.child("year").getValue().toString();
                semester = snapshot.child("semester").getValue().toString();
                courseCodeAdapterHelper(dept,year,semester);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void courseCodeAdapterHelper(String dept,String year,String semester){
        DatabaseReference courseFirebase = FirebaseDatabase.getInstance().getReference().child("Courses").child(dept).child(year).child(semester);
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
                courseCodeAdapter = new ArrayAdapter<>(ShowMarks.this,R.layout.items_list,allCourses);
                showCodeTextView.setAdapter(courseCodeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}