package com.example.smartclassroutinefinalinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileStudent extends AppCompatActivity {

    TextView name, phone, presentAddress,permanentAddress, fatherName,motherName;
    Button update;
    String Name,Phone,ID,dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_student);
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.phoneNumber);
        presentAddress = findViewById(R.id.presentAddress);
        permanentAddress = findViewById(R.id.permanentAddress);
        fatherName = findViewById(R.id.fatherName);
        motherName = findViewById(R.id.motherName);
        update = findViewById(R.id.update);

        Name = ProfileStudent.Name;
        Phone = ProfileStudent.Phone;
        ID = ProfileStudent.ID;
        dept = ProfileStudent.Dept;

        name.setText(Name);
        phone.setText(Phone);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_Profile();
                Toast.makeText(EditProfileStudent.this, "Your Profile is Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileStudent.this, ProfileStudent.class));
            }
        });
    }

    public void Update_Profile() {
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userTable, studentTable;
        userTable = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
        studentTable = FirebaseDatabase.getInstance().getReference().child("Students").child(dept).child(ID);

        String Name, Phone, PresentAddress, PermanentAddress, FatherName,MotherName;
        Name = name.getText().toString();
        Phone = phone.getText().toString();
        FatherName = fatherName.getText().toString();
        MotherName = motherName.getText().toString();
        PresentAddress = presentAddress.getText().toString();
        PermanentAddress = permanentAddress.getText().toString();

        userTable.child("name").setValue(Name);
        userTable.child("phone").setValue(Phone);
        userTable.child("fatherName").setValue(FatherName);
        userTable.child("motherName").setValue(MotherName);
        userTable.child("presentAddress").setValue(PresentAddress);
        userTable.child("permanentAddress").setValue(PermanentAddress);

        studentTable.child("name").setValue(Name);
        studentTable.child("phone").setValue(Phone);
        studentTable.child("fatherName").setValue(FatherName);
        studentTable.child("motherName").setValue(MotherName);
        studentTable.child("presentAddress").setValue(PresentAddress);
        studentTable.child("permanentAddress").setValue(PermanentAddress);
    }
}