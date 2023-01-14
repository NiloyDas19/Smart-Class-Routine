package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Assign_Marks_ListView extends AppCompatActivity {
    Assign_Marks_Adapter adapter;
    ArrayList<String> studentIdList;
    String year,semester,dept;
    RecyclerView recyclerView;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_marks_list_view);

        recyclerView = findViewById(R.id.recyclerViewMarkAssign);
        submit = findViewById(R.id.submit);

        studentIdList = new ArrayList<>();
        adapter = new Assign_Marks_Adapter(Assign_Marks_ListView.this,studentIdList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        year = AssignMarks.year;
        semester = AssignMarks.semester;
        dept = AssignMarks.dept;

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                submitMark();
//            }
//        });


        submitMark();
    }

    @Override
    protected void onStart() {
        DatabaseReference studentTable = FirebaseDatabase.getInstance().getReference().child("Students").child(dept).child(year).child(semester);
        studentTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentIdList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    studentIdList.add(dataSnapshot.getKey());
                }
                studentIdList.add("1");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onStart();
    }

    public void submitMark(){
        for (String id : Assign_Marks_Adapter.hashMap.keySet()){
            for (String type : Assign_Marks_Adapter.hashMap.get(id).keySet()){
                Log.i("aaaaa",id+" "+type + " " + Assign_Marks_Adapter.hashMap.get(id).get(type));
            }
        }
    }
}