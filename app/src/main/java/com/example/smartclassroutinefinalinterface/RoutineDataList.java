package com.example.smartclassroutinefinalinterface;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smartclassroutinefinalinterface.weekdays.DomainRow;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutineDataList {
    List<DomainRow > rowDami;
    List<DomainRow > row;
    CallbackRow callbackRow;
    DatabaseReference routineTable;
   public RoutineDataList()
   {
      row = new ArrayList<>();
      rowDami = new ArrayList<>();
   }

   public void getRoutineData(String year,String semester){
       for (int i=1;i<6;i++){
           getData(year,semester,getDay(i));
       }
   }

   public void fetchData(CallbackRow callbackRow){
       this.callbackRow=callbackRow;
        String semester = BeforeRoutine.semester;
       getRoutineData("1st",semester);
       getRoutineData("2nd",semester);
       getRoutineData("3rd",semester);
       getRoutineData("4th",semester);

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

    public void getData(String year,String semester,String day){
        routineTable = FirebaseDatabase.getInstance().getReference().child("Routine").child("CSE").child(year).child(semester).child(day);
        routineTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DomainRow info=new DomainRow();
                info._1 = year;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    if(dataSnapshot.getKey().equals("0920"))            info._2 = dataSnapshot.getValue().toString();
                    else if (dataSnapshot.getKey().equals("1020"))      info._3 = dataSnapshot.getValue().toString();
                    else if (dataSnapshot.getKey().equals("1120"))      info._4 = dataSnapshot.getValue().toString();
                    else if (dataSnapshot.getKey().equals("1220"))      info._5 = dataSnapshot.getValue().toString();
                    else if (dataSnapshot.getKey().equals("0120"))      info._6 = dataSnapshot.getValue().toString();
                    else if (dataSnapshot.getKey().equals("0220"))      info._7 = dataSnapshot.getValue().toString();
                    else if (dataSnapshot.getKey().equals("0320"))      info._8 = dataSnapshot.getValue().toString();
                    else if (dataSnapshot.getKey().equals("0420"))      info._9 = dataSnapshot.getValue().toString();
                }

                rowDami.add(info);
                if(rowDami.size()==20)
                {
                    for (int i=0,j=5,k=10,l=15;l<rowDami.size();i++,j++,k++,l++){
                        row.add(rowDami.get(i));
                        row.add(rowDami.get(j));
                        row.add(rowDami.get(k));
                        row.add(rowDami.get(l));
                    }
                    callbackRow.received(row);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


}
