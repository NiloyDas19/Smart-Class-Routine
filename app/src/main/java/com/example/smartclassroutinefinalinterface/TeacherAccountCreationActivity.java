package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class TeacherAccountCreationActivity extends AppCompatActivity {

    private Button signUpBtn;
    private EditText Name;
    private EditText UserName;
    private EditText Email;
    private EditText Password;
    private EditText Phone;
    private EditText TeacherId;
    TextInputLayout deptLayout,designationLayout;
    AutoCompleteTextView deptTextView,designationTextView;
    String dept,designation;
    private boolean Result;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userTable,teacherTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_account_creation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        teacherTable = FirebaseDatabase.getInstance().getReference().child("Teachers");
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");

        Name = (EditText) findViewById(R.id.Name);
        UserName = (EditText) findViewById(R.id.UserName);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Phone = findViewById(R.id.ContactNo);
        TeacherId = findViewById(R.id.TeacherId);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        deptLayout = findViewById(R.id.deptDropDownLayout);
        deptTextView = findViewById(R.id.deptDropDownTextView);

        String[] allDept = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(TeacherAccountCreationActivity.this,R.layout.items_list,allDept);
        deptTextView.setAdapter(deptAdapter);

        designationLayout = findViewById(R.id.designationDropDownLayout);
        designationTextView = findViewById(R.id.designationDropDownTextView);

        String[] allDesignations = {"Professor","Associate Professor","Assistant Professor","Lecturer"};
        ArrayAdapter<String> designationAdapter = new ArrayAdapter<>(TeacherAccountCreationActivity.this,R.layout.items_list,allDesignations);
        designationTextView.setAdapter(designationAdapter);

        deptTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(TeacherAccountCreationActivity.this, deptAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        designationTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                designation = designationAdapter.getItem(i);
                Toast.makeText(TeacherAccountCreationActivity.this, designationAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    String User_mail = Email.getText().toString().trim();
                    String User_Pass = Password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(User_mail,User_Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String currentUserID = firebaseAuth.getCurrentUser().getUid().toString();

                                String name,userName,email,phone,password,teacherID;
                                name = Name.getText().toString();
                                userName = UserName.getText().toString();
                                email = Email.getText().toString();
                                phone = Phone.getText().toString();
                                password = Password.getText().toString();
                                teacherID = TeacherId.getText().toString();

                                Teachers teachers = new Teachers(dept,designation,name,teacherID,phone,email,password,userName,null,null,null,null,"Teacher");

                                teacherTable.child(dept).child(teacherID).setValue(teachers);
                                userTable.child(currentUserID).setValue(teachers);
                                sendVerificationEmail();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthUserCollisionException){
                                ShowMsg(Email,"This Email Already Registered");
                            }
                            else {
                                Toast.makeText(TeacherAccountCreationActivity.this,"Ups!!! Something Went Wrong. SignUp UnSuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void signUp(){
        Intent intent = new Intent(TeacherAccountCreationActivity.this,TeacherPannel.class);
        startActivity(intent);
    }

    private boolean validate(){
        Result = false;
        String name = Name.getText().toString();
        String username = UserName.getText().toString();
        String email = Email.getText().toString();
        String Pass = Password.getText().toString();
        if (name.isEmpty()){
            ShowMsg(Name,"Name can't be empty");
        }
        else if (email.isEmpty()){
            ShowMsg(Email,"Email can't be empty");
        }
        else if (!email.contains("@")){
            ShowMsg(Email,"Invalid email address");
        }
        else if (username.isEmpty()){
            ShowMsg(UserName,"User name can't be empty");
        }
        else if (Pass.isEmpty()){
            ShowMsg(Password,"Password can't be empty");
        }
        else if (Pass.length()<8){
            ShowMsg(Password,"Password length must be at least 8");
        }
        else {
            Result = true;
        }
        return Result;
    }
    private void ShowMsg(EditText filled, String msg){
        filled.setError(msg);
        filled.requestFocus();
    }

    public void sendVerificationEmail(){
        if (firebaseAuth.getCurrentUser()!=null){
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(TeacherAccountCreationActivity.this, "Registration Successful. Please! Verify your email and try to login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else {
                        Toast.makeText(TeacherAccountCreationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}