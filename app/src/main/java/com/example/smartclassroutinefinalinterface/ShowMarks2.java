package com.example.smartclassroutinefinalinterface;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowMarks2 extends AppCompatActivity {

    Toolbar toolbar;
    TextView mark,courseCode,typeAndNumber;
    String markStr;
    String code,type,number;
    String dept,year,semester,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_marks2);

        toolbar = findViewById(R.id.ShowMarks2Toolbar);
        mark = findViewById(R.id.mark);
        courseCode = findViewById(R.id.courseCode);
        typeAndNumber = findViewById(R.id.typeAndNumber);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        code = ShowMarks.code;
        type = ShowMarks.type;
        number = ShowMarks.number;

        courseCode.setText(code+" ");
        typeAndNumber.setText(type+number+" ");

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dept = snapshot.child("dept").getValue().toString();
                year = snapshot.child("year").getValue().toString();
                semester = snapshot.child("semester").getValue().toString();
                id = snapshot.child("studentID").getValue().toString();
                markShow(dept,year,semester,id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void markShow(String dept,String year,String semester,String id){
        DatabaseReference student = FirebaseDatabase.getInstance().getReference().child("Students").child(dept).child(year).child(semester).child(id);

        student.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (number.equals("Final") || type.equals("Lab")){
                    double marks = 0;
                    int cnt=0;
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if (dataSnapshot.getKey().contains(type)){
                            cnt++;
                            marks += Double.parseDouble(dataSnapshot.getValue().toString());
                        }
                    }
                    marks = marks/(cnt*1.00);

                    mark.setText(Double.toString(marks));
                }
                else if (snapshot.child(code+type+number).exists()) {
                    markStr = snapshot.child(code + type + number).getValue().toString();
                    if (markStr != null)
                        mark.setText(markStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}