package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.core.UserData;
import com.google.firestore.v1.Document;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAccountCreationActivity extends AppCompatActivity {

    private EditText Name;
    private EditText userName;
    private EditText Email;
    private EditText Password;
    private EditText Phone,id;
    private Button signUpBtn;
    TextInputLayout deptLayout;
    AutoCompleteTextView deptTextView;
    String dept;
    private boolean check_Result = false;
    private boolean Result = false;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userTable,adminTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_creation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        adminTable = FirebaseDatabase.getInstance().getReference().child("Admins");
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");



        Name = (EditText) findViewById(R.id.Name);
        Email = (EditText) findViewById(R.id.Email);
        userName = (EditText) findViewById(R.id.userName);
        Password = (EditText) findViewById(R.id.password);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        Phone = findViewById(R.id.phoneNumber);
        id = findViewById(R.id.adminID);
        deptLayout = findViewById(R.id.deptDropDownLayout);
        deptTextView = findViewById(R.id.deptDropDownTextView);

        String[] allDept = {"CSE","EEE","ChE","BME","TE","PME","IPE","Microbiology","FMB","GEBT","Pharmacy","Nursing and Health Science"};
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(AdminAccountCreationActivity.this,R.layout.items_list,allDept);
        deptTextView.setAdapter(deptAdapter);

        deptTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept = deptAdapter.getItem(i);
                Toast.makeText(AdminAccountCreationActivity.this, deptAdapter.getItem(i), Toast.LENGTH_SHORT).show();
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
                                String name,UserName,email,phone,password,ID;
                                name = Name.getText().toString();
                                UserName = userName.getText().toString();
                                ID = id.getText().toString();
                                email = Email.getText().toString();
                                phone = Phone.getText().toString();
                                password = Password.getText().toString();
                                Admin admin = new Admin(name,UserName,ID,email,phone,password,dept,"Admin",null,null);

                                adminTable.child(dept).child(ID).setValue(admin);
                                userTable.child(currentUserID).setValue(admin);
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
                                Toast.makeText(AdminAccountCreationActivity.this,"Ups!!! Something Went Wrong. SignUp UnSuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void signUp(){
        Intent intent = new Intent(AdminAccountCreationActivity.this,AdminPannel.class);
        startActivity(intent);
    }

    private boolean validate(){
        String name = Name.getText().toString();
        String username = userName.getText().toString();
        String email = Email.getText().toString();
        String Pass = Password.getText().toString();
        String phone = Phone.getText().toString();

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
            ShowMsg(userName,"User name can't be empty");
        }
        else if (Pass.isEmpty()){
            ShowMsg(Password,"Password can't be empty");
        }
        else if (phone.isEmpty()){
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


    private void ShowMsg(EditText filled,String msg){
        filled.setError(msg);
        filled.requestFocus();
    }

    public void sendVerificationEmail(){
        if (firebaseAuth.getCurrentUser()!=null){
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AdminAccountCreationActivity.this, "Registration Successful. Please! Verify your email and try to login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else {
                        Toast.makeText(AdminAccountCreationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}