package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateRoutine extends AppCompatActivity {
    Toolbar CRtoolbar;
    EditText CRdept,CRyear,CRsemester,CRcode,CRtitle,CRname,CRstarttime,CRendtime,CRday;
    Button CRbutton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);


        CRtoolbar = findViewById(R.id.CRtoolbar);

        CRdept = findViewById(R.id.CRdept);
        CRyear = findViewById(R.id.CRyear);
        CRsemester = findViewById(R.id.CRsemester);
        CRcode = findViewById(R.id.CRcode);
        CRtitle = findViewById(R.id.CRtitle);
        CRname = findViewById(R.id.CRname);
        CRstarttime= findViewById(R.id.CRstarttime);
        CRendtime = findViewById(R.id.CRendtime);
        CRday = findViewById(R.id.CRday);
        CRbutton = findViewById(R.id.CRlogin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        root = firebaseDatabase.getReference().child("Classes");
        setSupportActionBar(CRtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CRbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dept = CRdept.getText().toString();
                String year = CRyear.getText().toString();
                String semester = CRsemester.getText().toString();
                String code = CRcode.getText().toString();
                String title = CRtitle.getText().toString();
                String teacher = CRname.getText().toString();
                String start = CRstarttime.getText().toString();
                String end = CRendtime.getText().toString();
                String day = CRday.getText().toString();

                HashMap<String,String> ClassDetails = new HashMap<>();
                ClassDetails.put("Dept",dept);
                ClassDetails.put("Year",year);
                ClassDetails.put("Semester",semester);
                ClassDetails.put("Course Title",title);
                ClassDetails.put("Course Code",code);
                ClassDetails.put("Teacher Name",teacher);
                ClassDetails.put("Class Start Time ",start);
                ClassDetails.put("Class End Time",end);
                ClassDetails.put("Day",day);
                root.push().setValue(ClassDetails);

                Toast.makeText(CreateRoutine.this,"Routine Created",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateRoutine.this,AdminPannel.class);


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