package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class UpdateRoutine<s> extends AppCompatActivity {

    Toolbar URtoolbar;
    TableView tableView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference day1,day2,day3,day4,day5,ref;
    String[][]  table = new String[6][9];
    //String[][]  table1 = GenerateRoutine.getTable();
    HashMap<String, Vector<String>>     m;

    {
        m = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_routine);

//        URtoolbar = findViewById(R.id.URtoolbar);
//        setSupportActionBar(URtoolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tableView = findViewById(R.id.tableView);
        getRoutineValue();
        helper2();

        String[] headers = {"a","b","c","d","e","e","f","g"};
        String[][] data = {{"sopon","joy","masum","sojib","refat"},
                            {"romel","rafi","nishat","rana","moba"}};

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,headers));
        //tableView.setDataAdapter(new SimpleTableDataAdapter(this,table1));



    }

    public  void getRoutineValue(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference().child("Routine").child("CSE").child("2nd").child("2nd");
        day1 = ref.child("Sat");
        day2 = ref.child("Sun");
        day3 = ref.child("Mon");
        day4 = ref.child("Tue");
        day5 = ref.child("Wed");

        helper1(day1,"Sat");
        helper1(day2,"Sun");
        helper1(day3,"Mon");
        helper1(day4,"Tue");
        helper1(day5,"Wed");
    }

    public void helper1(DatabaseReference reference,String day){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String time = dataSnapshot.getKey();
                    String course = dataSnapshot.getValue().toString();
                    if (m.get(day) == null) {
                        Vector<String> vector = new Vector<String>();
                        vector.add(time);
                        vector.add(course);
                        m.put(day,vector);
                    }
                    else{
                        m.get(day).add(course);
                        m.get(day).add(course);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void helper2(){
        for (Map.Entry<String,Vector<String>> mapElement : m.entrySet()){
            Vector<String> vector = mapElement.getValue();
            String day = mapElement.getKey();
            Iterator times = vector.iterator();
            int i,j;
            i=getI(day);
            Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
            while (times.hasNext()){
                String time = (String) times.next();
                j=getJ(time);
                Toast.makeText(this, i+" "+j, Toast.LENGTH_SHORT).show();
                table[i][j] = (String) times.next();
                Toast.makeText(this, table[i][j], Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getI(String day){
        int i=0;
        if (day.equals("Sat"))          i=1;
        else if (day.equals("Sun"))     i=2;
        else if (day.equals("Mon"))     i=3;
        else if (day.equals("Tue"))     i=4;
        else if (day.equals("Wed"))     i=5;
        return i;
    }
    public int getJ(String time){
        int j=0;
        if (time.equals("0920"))        j=1;
        else if (time.equals("1020"))   j=2;
        else if (time.equals("1120"))   j=3;
        else if (time.equals("1220"))   j=4;
        else if (time.equals("0120"))   j=5;
        else if (time.equals("0220"))   j=6;
        else if (time.equals("0320"))   j=7;
        else if (time.equals("0420"))   j=8;
        return  j;
    }
}