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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddTeacher extends AppCompatActivity {

    Toolbar ATtoolbar;
    TextInputLayout ATdept;
    AutoCompleteTextView ATdepttv;
    EditText teacherName,teacherId;
    Button submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference root;
    String dept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        ATtoolbar = findViewById(R.id.ATtoolbar);
        setSupportActionBar(ATtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ATdept = findViewById(R.id.ATdept);
        ATdepttv = findViewById(R.id.ATdepttv);


        String[] alldepts = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science","EST","NFT","APPT","Climate and Disaster Management","PESS","Physiotherapy and Rehabilitaion","English","Physics","Chemistry","Mathematics","AIS","Managment","Finance and Bancking","Marketing"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(AddTeacher.this,R.layout.items_list,alldepts);
        ATdepttv.setAdapter(deptAdapter);

        ATdepttv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(AddTeacher.this, dept, Toast.LENGTH_SHORT).show();
            }
        });

        teacherName = findViewById(R.id.ATname);
        teacherId = findViewById(R.id.ATid);
        submit = findViewById(R.id.ATlogin);
        firebaseDatabase = FirebaseDatabase.getInstance();
        root = firebaseDatabase.getReference().child("Teachers");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = teacherName.getText().toString();
                String id = teacherId.getText().toString();
                Teachers teachers = new Teachers(dept,name,id);
                root.child(dept).child(id).setValue(teachers);
                Toast.makeText(AddTeacher.this, "Teacher Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddTeacher.this,AdminPannel.class));
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