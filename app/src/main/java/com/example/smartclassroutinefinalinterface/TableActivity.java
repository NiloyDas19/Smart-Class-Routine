package com.example.smartclassroutinefinalinterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.smartclassroutinefinalinterface.weekdays.DomainRow;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {
    RecyclerView recycler;
    List<List<DomainRow>> Outerlist;
    CallbackRow callbackRow=new CallbackRow() {
      @Override
      public void received(List<DomainRow> list) {
          //updateAdapter(list);
          f(list);
      }
  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        recycler=findViewById(R.id.recycler);
        new RoutineDataList().fetchData(callbackRow);
        Outerlist=new ArrayList<>();


    }

    void updateAdapter2()
    {
        TableAdapter2 adapter=new TableAdapter2(this,Outerlist);
        recycler.setLayoutManager(new LinearLayoutManager(TableActivity.this));
        recycler.setAdapter(adapter);

    }

    private void f(List<DomainRow> list)
    {
        for (int i=0;i<20;i+=4){
            List<DomainRow> tmp=new ArrayList<>();
            tmp.add(list.get(i));
            tmp.add(list.get(i+1));
            tmp.add(list.get(i+2));
            tmp.add(list.get(i+3));
            Outerlist.add(tmp);
        }
        updateAdapter2();
    }

}
