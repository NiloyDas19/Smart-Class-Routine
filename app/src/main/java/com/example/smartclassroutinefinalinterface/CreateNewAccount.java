package com.example.smartclassroutinefinalinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNewAccount extends AppCompatActivity {

    private Button CreateStuAcc,CreateTecAcc,CreateAdmAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        CreateAdmAcc = (Button) findViewById(R.id.btnAdmin);
        CreateTecAcc = (Button) findViewById(R.id.btnTeacher);
        CreateStuAcc = (Button) findViewById(R.id.btnStudent);

        CreateAdmAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateNewAccount.this,AdminAccountCreationActivity.class));
            }
        });
        CreateTecAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateNewAccount.this,TeacherAccountCreationActivity.class));
            }
        });
        CreateStuAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateNewAccount.this,StudentAccountCreationActivity.class));
            }
        });
    }
}