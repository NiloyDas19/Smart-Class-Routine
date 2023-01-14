package com.example.smartclassroutinefinalinterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

public class ShowNotice extends AppCompatActivity {

    ListView listView;
    List<FileClass> pdfFiles;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notice);

        listView = findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notices");
        pdfFiles = new ArrayList<>();

        retrieveNotices();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FileClass pdf = pdfFiles.get(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(pdf.getUrl()));
                startActivity(intent);
            }
        });
    }

    void retrieveNotices(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfFiles.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    FileClass fileClass = dataSnapshot.getValue(FileClass.class);
                    pdfFiles.add(fileClass);
                }
                String[] descriptions = new String[pdfFiles.size()];
                for (int i=0;i<pdfFiles.size();i++){
                    descriptions[i] = pdfFiles.get(i).getDescription();
                }

                ArrayAdapter<String > arrayAdapter = new ArrayAdapter<String >(getApplicationContext(), android.R.layout.simple_list_item_1,descriptions){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView)view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return view;
                    }
                };
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}