package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseDistributionShowingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String dept = ShowCourseDistribution.dept;
    String year = ShowCourseDistribution.year;
    String semester = ShowCourseDistribution.semester;
    private ArrayList<DistributionClass> distributionList;
    DatabaseReference df;
    CourseDistributionShowingAdapter courseDistributionShowingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_distribution_showing);

        distributionList = new ArrayList<>();
        courseDistributionShowingAdapter = new CourseDistributionShowingAdapter(CourseDistributionShowingActivity.this,distributionList);
        recyclerView = findViewById(R.id.recyclerViewCourseDistributionShowing);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseDistributionShowingAdapter);
        df = FirebaseDatabase.getInstance().getReference("CourseDistribution").child(dept).child(year).child(semester);

        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DistributionClass distributionClass = dataSnapshot.getValue(DistributionClass.class);
                    distributionList.add(distributionClass);
                }
                courseDistributionShowingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}