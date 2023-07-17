package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileTeacher extends AppCompatActivity {

    TextView name,designation,dept,email,phone,id,presentAddress,permanentAddress,roomNo,education,userName;
    CircleImageView profilePic;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,pp;
    String currentUser;
    Button editProfile;
    static String Name,Designation,Dept,Email,Phone,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);

        name = findViewById(R.id.textViewName);
        designation = findViewById(R.id.textViewDesignation);
        dept = findViewById(R.id.textViewDept);
        email = findViewById(R.id.textViewEmail);
        phone = findViewById(R.id.textViewPhoneNumber);
        id = findViewById(R.id.textViewTeacherID);
        presentAddress = findViewById(R.id.textViewPresentAddress);
        permanentAddress = findViewById(R.id.textViewPermanentAddress);
        roomNo = findViewById(R.id.textViewRoomNo);
        education = findViewById(R.id.textViewEducation);
        userName = findViewById(R.id.textViewUserName);
        profilePic = findViewById(R.id.imageViewProfilePic);
        editProfile = findViewById(R.id.buttonEditProfile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference().child("Users").child(currentUser);

        pp = FirebaseDatabase.getInstance().getReference().child("ProfilePics");
        pp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(currentUser).exists()){
                    String url = snapshot.child(currentUser).child("url").getValue().toString();
                    Picasso.with(getApplicationContext()).load(url).into(profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teachers teachers = new Teachers();
                teachers = snapshot.getValue(Teachers.class);
                String nameStr, emailStr, phoneStr, roomNOStr, presentAddressStr, permanentAddressStr, educationStr, idStr, userNameStr, designationStr, deptStr;

                nameStr = teachers.name;
                emailStr = teachers.email;
                phoneStr = teachers.phone;
                roomNOStr = teachers.roomNo;
                presentAddressStr = teachers.presentAddress;
                permanentAddressStr = teachers.permanentAddress;
                educationStr = teachers.education;
                idStr = teachers.id;
                userNameStr = teachers.userName;
                designationStr = teachers.designation;
                deptStr = teachers.dept;

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
                if (roomNOStr == null) {
                    roomNo.setText("Not Provided");
                } else {
                    roomNo.setText(roomNOStr);
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
                if (educationStr == null) {
                    education.setText("Not Provided");
                } else {
                    education.setText(educationStr);
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
                if (designationStr == null) {
                    designation.setText("Not Provided");
                } else {
                    designation.setText(designationStr);
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

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Dept = dept.getText().toString();
                Designation = designation.getText().toString();
                Email = email.getText().toString();
                Phone = phone.getText().toString();
                ID = id.getText().toString();
                startActivity(new Intent(ProfileTeacher.this, EditProfileTeacher.class));
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