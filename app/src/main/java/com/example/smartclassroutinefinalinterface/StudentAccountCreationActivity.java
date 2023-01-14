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

public class StudentAccountCreationActivity extends AppCompatActivity {

    private Button signUpBtn;
    private EditText Name;
    private EditText UserName;
    private EditText Email;
    private EditText Password;
    private EditText Session;
    private EditText StudentId;
    private EditText PhoneNumber;
    TextInputLayout deptLayout,yearLayout,semesterLayout;
    AutoCompleteTextView deptTextView,yearTextView,semesterTextView;
    String dept,year,semester;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userTable,studentTable;
    private FirebaseAuth firebaseAuth;
    private boolean Result,check_Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account_creation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        studentTable = FirebaseDatabase.getInstance().getReference().child("Students");
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");

        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        Name = (EditText) findViewById(R.id.Name);
        UserName = (EditText) findViewById(R.id.UserName);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        StudentId = (EditText) findViewById(R.id.StudentId);
        Session = (EditText) findViewById(R.id.Session);
        PhoneNumber = findViewById(R.id.phoneNumber);

        deptLayout = findViewById(R.id.deptDropDownLayout);
        deptTextView = findViewById(R.id.deptDropDownTextView);
        yearLayout = findViewById(R.id.yearDropDownLayout);
        yearTextView = findViewById(R.id.yearDropDownTextView);
        semesterLayout = findViewById(R.id.semesterDropDownLayout);
        semesterTextView = findViewById(R.id.semesterDropDownTextView);

        String[] allDept = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(StudentAccountCreationActivity.this,R.layout.items_list,allDept);
        deptTextView.setAdapter(deptAdapter);

        String[] allyear = {"1st","2nd","3rd","4th"};
        String[] allsemester = {"1st","2nd"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(StudentAccountCreationActivity.this,R.layout.items_list,allyear);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(StudentAccountCreationActivity.this,R.layout.items_list,allsemester);
        yearTextView.setAdapter(yearAdapter);
        semesterTextView.setAdapter(semesterAdapter);

        deptTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(StudentAccountCreationActivity.this, deptAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        yearTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearAdapter.getItem(i);
                Toast.makeText(StudentAccountCreationActivity.this, year, Toast.LENGTH_SHORT).show();
            }
        });

        semesterTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                semester = semesterAdapter.getItem(i);
                Toast.makeText(StudentAccountCreationActivity.this, semester, Toast.LENGTH_SHORT).show();
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
                                String currentUserId = firebaseAuth.getCurrentUser().getUid().toString();

                                String name,userName,email,phone,password,studentID,session;
                                name = Name.getText().toString();
                                userName = UserName.getText().toString();
                                email = Email.getText().toString();
                                phone = PhoneNumber.getText().toString();
                                password = Password.getText().toString();
                                studentID = StudentId.getText().toString();
                                session = Session.getText().toString();

                                Student student = new Student(name,studentID,session,year,semester,dept,email,phone,null,null,null,null,userName,password,"Student");

                                studentTable.child(dept).child(year).child(semester).child(studentID).setValue(student);
                                userTable.child(currentUserId).setValue(student);

                                //root.push().setValue(userInfo);
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
                                Toast.makeText(StudentAccountCreationActivity.this,"Ups!!! Something Went Wrong. SignUp UnSuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void signUp(){
        Intent intent = new Intent(StudentAccountCreationActivity.this,StudentPannel.class);
        startActivity(intent);
    }

    private boolean validate(){
        Result = false;
        String name = Name.getText().toString();
        String username = UserName.getText().toString();
        String email = Email.getText().toString();
        String Pass = Password.getText().toString();
        String session = Session.getText().toString();
        String studentId = StudentId.getText().toString();

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
        else if (session.isEmpty()){
            ShowMsg(Session,"Session can't be empty");
        }
        else if (studentId.isEmpty()){
            ShowMsg(StudentId,"Student id can't be empty");
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
                        Toast.makeText(StudentAccountCreationActivity.this, "Registration Successful. Please! Verify your email and try to login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else {
                        Toast.makeText(StudentAccountCreationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}