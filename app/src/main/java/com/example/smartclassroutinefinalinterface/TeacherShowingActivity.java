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

public class TeacherShowingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TeacherAdapter teacherAdapter;
    private ArrayList<Teachers> teachersList;
    DatabaseReference databaseReference;
    String dept = ShowTeachers.getDept();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_showing_recycler_view);

        recyclerView = findViewById(R.id.recyclerViewTeacherShowing);
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers").child(dept);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        teachersList = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(this,teachersList);
        recyclerView.setAdapter(teacherAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Teachers teachers = dataSnapshot.getValue(Teachers.class);
                    teachersList.add(teachers);
                }
                teacherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}