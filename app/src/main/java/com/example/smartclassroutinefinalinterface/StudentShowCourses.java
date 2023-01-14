package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;
import java.util.ArrayList;

public class StudentShowCourses extends AppCompatActivity {

    String dept,semester,year;
    RecyclerView recyclerView;
    private ArrayList<Course> courseList;
    DatabaseReference courseTable;
    CourseShowingAdapter courseShowingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_courses);

        recyclerView = findViewById(R.id.recyclerViewStudentCourseShowing);

        courseList = new ArrayList<>();
        courseShowingAdapter = new CourseShowingAdapter(StudentShowCourses.this,courseList);
        recyclerView = findViewById(R.id.recyclerViewStudentCourseShowing);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseShowingAdapter);

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dept = snapshot.child("dept").getValue().toString();
                year = snapshot.child("year").getValue().toString();
                semester = snapshot.child("semester").getValue().toString();
                helper(dept,semester,year);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void helper(String dept,String semester, String year){
        courseTable = FirebaseDatabase.getInstance().getReference("Courses").child(dept).child(year).child(semester);

        courseTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Course course = dataSnapshot.getValue(Course.class);
                    courseList.add(course);
                }
                courseShowingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}