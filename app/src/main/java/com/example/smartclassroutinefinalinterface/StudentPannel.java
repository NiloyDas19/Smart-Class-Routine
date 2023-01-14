package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class StudentPannel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    CardView studentroutine, showNotice,studentprofile,showMarks,showCourse;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar studenttoolbar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pannel);

        studentroutine = findViewById(R.id.studentroutine);
        showNotice = findViewById(R.id.showNoticeLayout);
        studentprofile = findViewById(R.id.studentprofile);
        showMarks = findViewById(R.id.studentShowMarks);
        showCourse = findViewById(R.id.studentShowCourses);

        studentroutine.setOnClickListener(this);
        showNotice.setOnClickListener(this);
        studentprofile.setOnClickListener(this);
        showMarks.setOnClickListener(this);
        showCourse.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        studenttoolbar = findViewById(R.id.studenttoolbar);
        setSupportActionBar(studenttoolbar);

        nav = findViewById(R.id.studentnavmenu);
        drawerLayout = findViewById(R.id.student_drawer);

        toggle  = new ActionBarDrawerToggle(this,drawerLayout,studenttoolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if(id==R.id.student_menu_home){
            Toast.makeText(getApplicationContext(), "Home click", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), StudentPannel.class);
            startActivity(intent);
        }
        else if(id==R.id.student_menu_routine){
            Toast.makeText(getApplicationContext(), "Routine click", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(),BeforeRoutine.class);
            startActivity(intent);
        }
        else if(id==R.id.student_menu_Notices){
            Toast.makeText(getApplicationContext(), "Student Request", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(),ShowNotice.class);
            startActivity(intent);
        }
        else if(id==R.id.student_menu_ShowMarks){
            Toast.makeText(getApplicationContext(), "Student Request", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(),ShowMarks.class);
            startActivity(intent);
        }
        else if(id==R.id.student_menu_profile){
            Toast.makeText(getApplicationContext(),"ProfileStudent",Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), ProfileStudent.class);
            startActivity(intent);
        }
        else if(id==R.id.student_menu_logout){
            firebaseAuth.signOut();
            Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.studentroutine:
                intent = new Intent(this,BeforeRoutine.class);
                startActivity(intent);
                break;
            case R.id.showNoticeLayout:
                intent = new Intent(this,ShowNotice.class);
                startActivity(intent);
                break;
            case R.id.studentprofile:
                intent = new Intent(this, ProfileStudent.class);
                startActivity(intent);
                break;
            case R.id.studentShowMarks:
                intent = new Intent(this,ShowMarks.class);
                startActivity(intent);
                break;
            case R.id.studentShowCourses:
                intent = new Intent(this,StudentShowCourses.class);
                startActivity(intent);
                break;
        }
    }
}