package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenarateRoutine2 extends AppCompatActivity {

    Toolbar BRtoolbar;
    Button generateRoutine;
    TextInputLayout semesterLayout;
    AutoCompleteTextView semesterTV;
    String semester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genarate_routine2);

        BRtoolbar = findViewById(R.id.BRtoolbar);
        generateRoutine = findViewById(R.id.generateRoutine);
        semesterLayout = findViewById(R.id.semesterDropDownLayout);
        semesterTV = findViewById(R.id.semesterDropDownTextView);

        setSupportActionBar(BRtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String[] allSemester = {"1st","2nd"};
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(GenarateRoutine2.this,R.layout.items_list,allSemester);
        semesterTV.setAdapter(semesterAdapter);

        generateRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semester = semesterTV.getText().toString();
                findDept();
                Toast.makeText(GenarateRoutine2.this, "Routine Generated Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AdminPannel.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void findDept(){
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dept = snapshot.child("department").getValue().toString();;

                courses(dept);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void courses(String dept){
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("CourseDistribution").child(dept);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DistributionClass> courseInformations = new ArrayList<>();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Log.i("aaa",snapshot1.getKey());
                    for (DataSnapshot dataSnapshot: snapshot1.child(semester).getChildren()){
                        Log.i("aaa",dataSnapshot.getKey());
                        DistributionClass cd = dataSnapshot.getValue(DistributionClass.class);
                        courseInformations.add(cd);
                    }
                }
                Log.i("aaa", String.valueOf(courseInformations));
                generateRoutine(dept,courseInformations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void generateRoutine(String dept,List<DistributionClass> courseInformations){
        String [][][][] routine = new String[5][8][4][2];


        for(int i=0;i<5;i++){
            for(int j=0;j<8;j++){
                for(int k = 0; k < 4 ;k++){
                    if (j==4)
                        routine[i][j][k][0] = routine[i][j][k][1] = "Break";
                    else
                        routine[i][j][k][0] = routine[i][j][k][1] = "NoClass";
                }
            }
        }





        Random random = new Random();
        for(DistributionClass courseInformation:courseInformations){

            String CourseCode = courseInformation.getCourseCode();
            String TeachersID = courseInformation.getTeacherId();
            double credit = Double.valueOf(courseInformation.getCredit());
            if (courseInformation.getType().equals("Viva Voce"))    continue;
            if(courseInformation.getType().equals("Lab")) credit*=2.0;
            String yearString = courseInformation.getYear();

            int year = 0;
            if(yearString.equals("2nd")) year = 1;
            else if(yearString.equals("3rd")) year = 2;
            else if(yearString.equals("4th")) year = 3;

            while(credit > 0.0){
                int randomX = random.nextInt(5);
                int randomY = random.nextInt(8);
                if (randomY==4){
                    continue;
                }
                if(routine[randomX][randomY][year][1].equals(CourseCode)) continue;
                boolean isPreviouslyTaken = false;
                for(int j = 0;j<8;j++){
                    if(routine[randomX][j][year][1].equals(CourseCode)){
                        isPreviouslyTaken = true;
                        break;
                    }
                }
                if(!isPreviouslyTaken){
                    boolean isSirAvailable = true;
                    for(int k=0;k<4;k++){
                        if(routine[randomX][randomY][k][0].equals(TeachersID)){
                            isSirAvailable = false;
                            break;
                        }
                    }
                    if(isSirAvailable){
                        routine[randomX][randomY][year][0] = TeachersID;
                        routine[randomX][randomY][year][1] = CourseCode;
                        credit-=1.0;
                    }
                }
            }
        }
        uploadToDatabase(dept,routine);
    }

    void uploadToDatabase(String dept, String [][][][] routine){

        for (int i=0;i<5;i++){
            for (int j=0;j<8;j++){
                for (int k=0;k<4;k++){
                    //Log.i("aaa",routine[i][j][k][1]);

                    String year = getYear(k);
                    String day = getDay(i+1);
                    String time = getTime(j+1);
                    //Log.i("aaa",year+day+time);
                    DatabaseReference df = FirebaseDatabase.getInstance().getReference()
                            .child("Routine").child(dept).child(year)
                            .child(semester).child(day).child(time);

                    df.setValue(routine[i][j][k][1]);
                }
            }
        }


    }

    public String getDay(int i){
        String day = "Fri";
        if (i==1)           day = "Sat";
        else if (i==2)      day = "Sun";
        else if (i==3)      day = "Mon";
        else if (i==4)      day = "Tue";
        else if (i==5)      day = "Wed";

        return day;
    }

    public String getTime(int j){
        String time = "0820";
        if (j==1)           time = "0920";
        else if (j==2)      time = "1020";
        else if (j==3)      time = "1120";
        else if (j==4)      time = "1220";
        else if (j==5)      time = "0120";
        else if (j==6)      time = "0220";
        else if (j==7)      time = "0320";
        else if (j==8)      time = "0420";

        return  time;
    }

    public String getYear(int k){
        String year = "0th";
        if(k==0)    year="1st";
        else if (k==1)  year="2nd";
        else if (k==2)  year="3rd";
        else if (k==3)  year="4th";

        return year;
    }

}