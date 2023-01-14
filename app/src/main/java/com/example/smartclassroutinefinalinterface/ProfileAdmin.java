package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartclassroutinefinalinterface.weekdays.DomainRow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileAdmin extends AppCompatActivity {

    ImageView profilePic;
    TextView name,dept,userName,id,presentAddress,permanentAddress,email,phone;
    Button editProfile;
    DatabaseReference user;
    String currentUser;
    static String Name,Dept,ID,Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        profilePic = findViewById(R.id.imageViewProfilePic);
        name = findViewById(R.id.textViewName);
        dept = findViewById(R.id.textViewDept);
        userName = findViewById(R.id.textViewUserName);
        id = findViewById(R.id.textViewID);
        presentAddress = findViewById(R.id.textViewPresentAddress);
        permanentAddress = findViewById(R.id.textViewPermanentAddress);
        email = findViewById(R.id.textViewEmail);
        phone = findViewById(R.id.textViewPhoneNumber);
        editProfile = findViewById(R.id.buttonEditProfile);

        setData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Dept = dept.getText().toString();
                ID = id.getText().toString();
                Phone = phone.getText().toString();

                startActivity(new Intent(ProfileAdmin.this,EditProfileAdmin.class));
            }
        });

    }

    void setData(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin admin = new Admin();
                admin = snapshot.getValue(Admin.class);

                String nameStr, emailStr, phoneStr, presentAddressStr, permanentAddressStr, idStr, userNameStr, deptStr;

                nameStr = admin.name;
                emailStr = admin.email;
                phoneStr = admin.phone;
                presentAddressStr = admin.presentAddress;
                permanentAddressStr = admin.permanentAddress;
                idStr = admin.id;
                userNameStr = admin.userName;
                deptStr = admin.dept;

                if (nameStr == null) {
                    name.setText("Not Provided");
                } else {
                    name.setText(nameStr);
                }
                if (emailStr == null) {
                    email.setText("Not Provided");
                } else {
                    email.setText(emailStr);
                }
                if (phoneStr == null) {
                    phone.setText("Not Provided");
                } else {
                    phone.setText(phoneStr);
                }
                if (presentAddressStr == null) {
                    presentAddress.setText("Not Provided");
                } else {
                    presentAddress.setText(presentAddressStr);
                }
                if (permanentAddressStr == null) {
                    permanentAddress.setText("Not Provided");
                } else {
                    permanentAddress.setText(permanentAddressStr);
                }
                if (idStr == null) {
                    id.setText("Not Provided");
                } else {
                    id.setText(idStr);
                }
                if (userNameStr == null) {
                    userName.setText("Not Provided");
                } else {
                    userName.setText(userNameStr);
                }
                if (deptStr == null) {
                    dept.setText("Not Provided");
                } else {
                    dept.setText(deptStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}