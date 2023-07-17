package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ProfileStudent extends AppCompatActivity {

    TextView name,dept,email,phone,id,presentAddress,permanentAddress,userName,fatherName,motherName,session;
    CircleImageView profilePic;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,pp;
    String currentUser;
    Button editProfile;
    static String Name,Dept,Phone,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);

        name = findViewById(R.id.textViewName);
        dept = findViewById(R.id.textViewDept);
        email = findViewById(R.id.textViewEmail);
        phone = findViewById(R.id.textViewPhoneNumber);
        id = findViewById(R.id.textViewStudentID);
        presentAddress = findViewById(R.id.textViewPresentAddress);
        permanentAddress = findViewById(R.id.textViewPermanentAddress);
        fatherName = findViewById(R.id.textViewFathersName);
        motherName = findViewById(R.id.textViewMothersName);
        userName = findViewById(R.id.textViewUserName);
        session = findViewById(R.id.textViewSeassion);
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
                Student student = new Student();
                student = snapshot.getValue(Student.class);
                String nameStr, emailStr, phoneStr, presentAddressStr, permanentAddressStr, idStr, userNameStr, sessionStr, deptStr, fathersNameStr,mothersNameStr;

                nameStr = student.name;
                emailStr = student.email;
                phoneStr = student.phone;
                sessionStr = student.session;
                presentAddressStr = student.presentAddress;
                permanentAddressStr = student.permanentAddress;
                idStr = student.studentID;
                userNameStr = student.userName;
                deptStr = student.dept;
                fathersNameStr = student.fatherName;
                mothersNameStr = student.motherName;

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
                if (sessionStr == null) {
                    session.setText("Not Provided");
                } else {
                    session.setText(sessionStr);
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
                if (fathersNameStr == null){
                    fatherName.setText("Not Provided");
                } else{
                    fatherName.setText(fathersNameStr);
                }
                if (mothersNameStr==null){
                    motherName.setText("Not Provided");
                } else{
                    motherName.setText(mothersNameStr);
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
                Phone = phone.getText().toString();
                ID = id.getText().toString();
                startActivity(new Intent(ProfileStudent.this, EditProfileStudent.class));
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