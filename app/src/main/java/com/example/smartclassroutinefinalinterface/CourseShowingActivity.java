package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseShowingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String dept = ShowCourses.getDept();
    String year = ShowCourses.getYear();
    String semester = ShowCourses.getSemester();
    private ArrayList<Course> courseList;
    DatabaseReference courseTable;
    CourseShowingAdapter courseShowingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_showing_recycler_view);

        courseList = new ArrayList<>();
        courseShowingAdapter = new CourseShowingAdapter(CourseShowingActivity.this,courseList);
        recyclerView = findViewById(R.id.recyclerViewCourseShowing);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseShowingAdapter);
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