//package com.example.smartclassroutinefinalinterface;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.example.smartclassroutinefinalinterface.weekdays.MONFragment;
//import com.example.smartclassroutinefinalinterface.weekdays.SATFragment;
//import com.example.smartclassroutinefinalinterface.weekdays.SUNFragment;
//import com.example.smartclassroutinefinalinterface.weekdays.THUFragment;
//import com.example.smartclassroutinefinalinterface.weekdays.TUEFragment;
//import com.example.smartclassroutinefinalinterface.weekdays.WEDFragment;
//import com.google.android.material.tabs.TabLayout;
//
//public class Routine extends AppCompatActivity {
//
//    Toolbar Rtoolbar;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_routine);
//
//        Rtoolbar = findViewById(R.id.Rtoolbar);
//        setSupportActionBar(Rtoolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//
//        tabLayout = findViewById(R.id.tab_layout);
//        viewPager = findViewById(R.id.viewpager);
//
//        tabLayout.setupWithViewPager(viewPager);
//        VPadapter  vPadapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        vPadapter.addFragement(new SATFragment(),"SAT");
//        vPadapter.addFragement(new SUNFragment(),"SUN");
//        vPadapter.addFragement(new MONFragment(),"MON");
//        vPadapter.addFragement(new TUEFragment(),"TUE");
//        vPadapter.addFragement(new WEDFragment(),"WED");
//        vPadapter.addFragement(new TUEFragment(),"THU");
//        viewPager.setAdapter(vPadapter);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()==android.R.id.home){
//            this.finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}

package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Routine extends AppCompatActivity {

    TableView tableView;
    DatabaseReference routine;
    public static String semester = BeforeRoutine.semester;
    String year = "1st";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_routine);

        tableView = findViewById(R.id.tableView);

        String[] headers = {"Time","SAT","SUN","MON","TUE","WED"};

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));

        routine = FirebaseDatabase.getInstance().getReference().child("Routine").child("CSE").child(year).child(semester);
        routine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String[] day = new String[6];
                String[][] table = new String[9][6];
                String[][] temp = new String[6][8];
                int i = 1, j = 1;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    day[i] = dataSnapshot.getValue().toString();
                    i++;
                }

                for (i = 1; i < 6; i++) {
                    temp[i] = day[i].split(",");
                }

                for (i = 1; i < 6; i++) {
                    for (j = 0; j < 8; j++) {
                        String[] balCourse = temp[i][j].split("=");
                        if (j == 7)
                            table[j+1][i] = balCourse[1].substring(0, (balCourse[1].length() - 2));
                        else table[j+1][i] = balCourse[1];
                    }
                }

                table[1][0] = "09.20";
                table[2][0] = "10.20";
                table[3][0] = "11.20";
                table[4][0] = "12.20";
                table[5][0] = "01.20";
                table[6][0] = "02.20";
                table[7][0] = "03.20";
                table[8][0] = "04.20";
                tableView.setDataAdapter(new SimpleTableDataAdapter(Routine.this, table));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}