package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    Button Login,cNa,fPw;
    EditText Email;
    TextInputEditText Pass;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference root;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login =  findViewById(R.id.btnLogin);
        cNa =  findViewById(R.id.btnCna);
        fPw = findViewById(R.id.btnForgetPassword);
        Email = findViewById(R.id.etEmail);
        Pass = findViewById(R.id.passwordTextInputEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        root = firebaseDatabase.getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        ProgressDialog progressDialog;

        if (user != null && user.isEmailVerified()){
            String currentUser = user.getUid();
            DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
            users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userType = snapshot.child("userType").getValue().toString();

                    if(userType.equals("Admin")){
                        Intent intentResident = new Intent(MainActivity.this, AdminPannel.class);
                        intentResident.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentResident);
                        finish();
                    }else if(userType.equals("Teacher")){
                        Intent intentMain = new Intent(MainActivity.this, TeacherPannel.class);
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentMain);
                        finish();
                    }else if(userType.equals("Student")){
                        Intent intentMain = new Intent(MainActivity.this, StudentPannel.class);
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentMain);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser(Email.getText().toString(),Pass.getText().toString(),view);
            }
        });

        fPw.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,ForgetPassword.class);
            startActivity(intent);
        });

        cNa.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,CreateNewAccount.class);
            startActivity(intent);
        });

    }

    private void loginUser(final String userLoginEmail, final String userLoginPassword,View view) {
        firebaseAuth.signInWithEmailAndPassword(userLoginEmail, userLoginPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                forProgressbar(view);
                                Toast.makeText(MainActivity.this, "LogIn Successfully", Toast.LENGTH_SHORT).show();

                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                String RegisteredUserID = currentUser.getUid();

                                DatabaseReference jLoginDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);

                                jLoginDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String userType = dataSnapshot.child("userType").getValue().toString();

                                        if(userType.equals("Admin")){
                                            Intent intentResident = new Intent(MainActivity.this, AdminPannel.class);
                                            intentResident.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intentResident);
                                            finish();
                                        }else if(userType.equals("Teacher")){
                                            Intent intentMain = new Intent(MainActivity.this, TeacherPannel.class);
                                            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intentMain);
                                            finish();
                                        }else if(userType.equals("Student")){
                                            Intent intentMain = new Intent(MainActivity.this, StudentPannel.class);
                                            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intentMain);
                                            finish();
                                        }else{
                                            Toast.makeText(MainActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Please Verify Your Email First", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "LogIn Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void forProgressbar(View v)
    {
        // prepare for a progress bar dialog
        progressBar = new ProgressDialog(v.getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Log in ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        Thread thread  =  new Thread(new Runnable() {
            public void run() {
                doSomeTasks();
            }
        });

        thread.start();
    }


    public void doSomeTasks() {
        for(progressBarStatus=0;progressBarStatus<=100;progressBarStatus+=10) {
            try {
                Thread.sleep(100);
                progressBar.setProgress(progressBarStatus);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}