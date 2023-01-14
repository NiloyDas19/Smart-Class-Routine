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

public class TeacherPannel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CardView tproutine,tpprofile,uploadNotice,assignMarks,showCourseDistribution;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar tptoolbar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pannel);
        tproutine = findViewById(R.id.tp_routine);
        tpprofile = findViewById(R.id.tp_profile);
        uploadNotice = findViewById(R.id.uploadNotice);
        assignMarks = findViewById(R.id.assignMarks);
        showCourseDistribution = findViewById(R.id.showCourseDistribution);

        tproutine.setOnClickListener(this);
        tpprofile.setOnClickListener(this);
        uploadNotice.setOnClickListener(this);
        assignMarks.setOnClickListener(this);
        showCourseDistribution.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        tptoolbar = findViewById(R.id.teacher_toolbar);
        setSupportActionBar(tptoolbar);

        nav = findViewById(R.id.tpnavmenu);
        drawerLayout = findViewById(R.id.tpdrawer);

        toggle  = new ActionBarDrawerToggle(this,drawerLayout,tptoolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(this);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if(id==R.id.tpmenu_home){
            Toast.makeText(getApplicationContext(), "Home click", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(),TeacherPannel.class);
            startActivity(intent);
        }
        else if(id==R.id.tpmenu_routine){
            Toast.makeText(getApplicationContext(), "Routine click", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(),BeforeRoutine.class);
            startActivity(intent);
        }
        else if(id==R.id.tp_menu_profile){
            Toast.makeText(getApplicationContext(), "Create ProfileTeacher click", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), ProfileTeacher.class);
            startActivity(intent);
        }
        else if(id==R.id.tpmenu_logout){
            firebaseAuth.signOut();
            Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.tpmenu_UploadNotice){
            intent = new Intent(getApplicationContext(),UploadNoticeTeacher.class);
            startActivity(intent);
        }
        else if(id==R.id.tpmenu_AssignMark){
            intent = new Intent(getApplicationContext(),AssignMarks.class);
            startActivity(intent);
        }
        else if(id==R.id.tpmenu_ShowCourseDistribution){
            intent = new Intent(getApplicationContext(),ShowCourseDistribution.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.tp_routine:
                intent = new Intent(this,BeforeRoutine.class);
                startActivity(intent);
                break;
            case R.id.tp_profile:
                intent = new Intent(this, ProfileTeacher.class);
                startActivity(intent);
                break;
            case R.id.uploadNotice:
                intent = new Intent(this,UploadNoticeTeacher.class);
                startActivity(intent);
                break;
            case R.id.assignMarks:
                intent = new Intent(this,AssignMarks.class);
                startActivity(intent);
                break;
            case R.id.showCourseDistribution:
                intent = new Intent(this,ShowCourseDistribution.class);
                startActivity(intent);
                break;
        }
    }
}