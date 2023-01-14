package com.example.smartclassroutinefinalinterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class AssignMarks2 extends AppCompatActivity {
    Toolbar toolbar;
    TextInputLayout studentID;
    AutoCompleteTextView studentIDTextView;
    Button assignMarks2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_marks2);

        toolbar = findViewById(R.id.AssignMarks2Toolbar);
        studentID = findViewById(R.id.AssignMarks2StudentID);
        studentIDTextView = findViewById(R.id.AssignMarks2StudentIDTextView);
        assignMarks2Button = findViewById(R.id.btnAssignMarks2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        assignMarks2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignMarks2.this,AssignMarks2.class);
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
}