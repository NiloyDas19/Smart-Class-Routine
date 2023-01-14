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

public class AdminPannel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    CardView beforeRoutine,addCourse,addTeacher,courseDistribution,profile,showCourses,showTeachers,showCourseDistribution,generateRoutine,uploadNotice;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);
        beforeRoutine = findViewById(R.id.routine);
        addCourse = findViewById(R.id.addCourse);
        addTeacher = findViewById(R.id.addTeacher);
        courseDistribution = findViewById(R.id.courseDistribution);
        profile = findViewById(R.id.profile);
        showCourses = findViewById(R.id.showCourses);
        showTeachers = findViewById(R.id.showTeachers);
        showCourseDistribution = findViewById(R.id.showCourseDistribution);
        generateRoutine = findViewById(R.id.generateRoutine);
        uploadNotice = findViewById(R.id.uploadNotice);

        beforeRoutine.setOnClickListener(this);
        profile.setOnClickListener(this);
        addCourse.setOnClickListener(this);
        addTeacher.setOnClickListener(this);
        courseDistribution.setOnClickListener(this);
        showCourses.setOnClickListener(this);
        showTeachers.setOnClickListener(this);
        showCourseDistribution.setOnClickListener(this);
        generateRoutine.setOnClickListener(this);
        uploadNotice.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawer);

        toggle  = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(this);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if(id==R.id.menu_home){
            intent = new Intent(getApplicationContext(), AdminPannel.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_routine){
            intent = new Intent(getApplicationContext(),BeforeRoutine.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_course_distribution){
            intent = new Intent(getApplicationContext(),CourseDistribution.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_show_teachers){
            intent = new Intent(getApplicationContext(),ShowTeachers.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_show_course_distribution){
            intent = new Intent(getApplicationContext(),ShowCourseDistribution.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_generate_routine){
            intent = new Intent(getApplicationContext(),GenarateRoutine2.class);
            startActivity(intent);
        }

        else if(id==R.id.menu_add_course){
            intent = new Intent(getApplicationContext(),AddCourse.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_add_teacher){
            intent = new Intent(getApplicationContext(),AddTeacher.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_profile){
            intent = new Intent(getApplicationContext(), ProfileAdmin.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_show_courses){
            intent = new Intent(getApplicationContext(),ShowCourses.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_UploadNotice){
            intent = new Intent(getApplicationContext(),UploadNoticeAdmin.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_logout){
            firebaseAuth.signOut();
            intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.routine:
                intent = new Intent(this,BeforeRoutine.class);
                startActivity(intent);
                break;
            case R.id.showCourses:
                intent = new Intent(this,ShowCourses.class);
                startActivity(intent);
                break;
            case R.id.showTeachers:
                intent = new Intent(this,ShowTeachers.class);
                startActivity(intent);
                break;
            case R.id.showCourseDistribution:
                intent = new Intent(this,ShowCourseDistribution.class);
                startActivity(intent);
                break;
            case R.id.generateRoutine:
                intent = new Intent(this,GenarateRoutine2.class);
                startActivity(intent);
                break;
            case R.id.courseDistribution:
                intent = new Intent(this,CourseDistribution.class);
                startActivity(intent);
                break;
            case R.id.addCourse:
                intent = new Intent(this,AddCourse.class);
                startActivity(intent);
                break;
            case R.id.addTeacher:
                intent = new Intent(this,AddTeacher.class);
                startActivity(intent);
                break;
            case R.id.profile:
                intent = new Intent(this, ProfileAdmin.class);
                startActivity(intent);
                break;
            case R.id.uploadNotice:
                intent = new Intent(this,UploadNoticeAdmin.class);
                startActivity(intent);
                break;

        }
    }
}