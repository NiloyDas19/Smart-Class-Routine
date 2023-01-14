package com.example.smartclassroutinefinalinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileTeacher extends AppCompatActivity {

    TextView name,teacherID,phone,address,roomNo,graduation,masters,phd;
    TextInputLayout designationLayout;
    AutoCompleteTextView designationTextView;
    String designation,dept,preTeacherId;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_teacher);

        name = findViewById(R.id.Name);
        teacherID = findViewById(R.id.TeacherId);
        phone = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.Address);
        roomNo = findViewById(R.id.roomNO);
        graduation = findViewById(R.id.graduation);
        masters = findViewById(R.id.masters);
        phd = findViewById(R.id.phd);
        update = findViewById(R.id.update);

        designationLayout = findViewById(R.id.designationDropDownLayout);
        designationTextView = findViewById(R.id.designationDropDownTextView);

        String[] allDesignations = {"Professor","Associate Professor","Assistant Professor","Lecturer"};
        ArrayAdapter<String> designationAdapter = new ArrayAdapter<>(EditProfileTeacher.this,R.layout.items_list,allDesignations);
        designationTextView.setAdapter(designationAdapter);

        designationTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                designation = designationAdapter.getItem(i);
                Toast.makeText(EditProfileTeacher.this, designationAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        name.setText(ProfileTeacher.Name);
        phone.setText(ProfileTeacher.Phone);
        teacherID.setText(ProfileTeacher.ID);
        designationTextView.setText(ProfileTeacher.Designation,false);
        dept = ProfileTeacher.Dept;
        preTeacherId = ProfileTeacher.ID;

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_Profile();
                Toast.makeText(EditProfileTeacher.this, "Your Profile is Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileTeacher.this,ProfileTeacher.class));
            }
        });
    }

    public  void Update_Profile(){
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userTable,teacherTable;
        userTable = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
        teacherTable = FirebaseDatabase.getInstance().getReference().child("Teachers").child(dept);
        Toast.makeText(this, preTeacherId, Toast.LENGTH_SHORT).show();
        teacherTable.child(preTeacherId).removeValue();
        String Name,TeacherId,Phone,Address,RoomNo,Graduation,Masters,PhD;
        Name = name.getText().toString();
        TeacherId = teacherID.getText().toString();
        Phone = phone.getText().toString();
        Address = address.getText().toString();
        RoomNo = roomNo.getText().toString();
        Graduation = graduation.getText().toString();
        Masters = masters.getText().toString();
        PhD = phd.getText().toString();

        userTable.child("name").setValue(Name);
        userTable.child("id").setValue(TeacherId);
        userTable.child("phone").setValue(Phone);
        userTable.child("address").setValue(Address);
        userTable.child("roomNo").setValue(RoomNo);
        userTable.child("education").setValue(Graduation+Masters+PhD);
        userTable.child("designation").setValue(designation);

        teacherTable.child(TeacherId).child("name").setValue(Name);
        teacherTable.child(TeacherId).child("id").setValue(TeacherId);
        teacherTable.child(TeacherId).child("phone").setValue(Phone);
        teacherTable.child(TeacherId).child("address").setValue(Address);
        teacherTable.child(TeacherId).child("roomNo").setValue(RoomNo);
        teacherTable.child(TeacherId).child("education").setValue(Graduation+Masters+PhD);
        teacherTable.child(TeacherId).child("designation").setValue(designation);

    }

}