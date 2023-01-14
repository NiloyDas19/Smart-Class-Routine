package com.example.smartclassroutinefinalinterface;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Assign_Marks_Adapter extends RecyclerView.Adapter<Assign_Marks_Adapter.MyViewHolder> {

    Context context;
    ArrayList<String> idList;
    public static HashMap<String  ,HashMap<String , String>> hashMap = new HashMap<>();
    String s = "";
    

    public Assign_Marks_Adapter(Context context, ArrayList<String> idList) {
        this.context = context;
        this.idList = idList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_assign_marks,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String id = idList.get(position);
        if (position == idList.size()-1){
            holder.submit.setVisibility(View.VISIBLE);
            holder.studentID.setVisibility(View.GONE);
            holder.space.setVisibility(View.GONE);
            holder.mark.setVisibility(View.GONE);
        }
        holder.studentID.setText(id);
        holder.mark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                HashMap<String , String > marks = new HashMap<>();
                s = holder.mark.getText().toString();
                String key = AssignMarks.type + AssignMarks.number;
                String idKey = holder.studentID.getText().toString();
                marks.put(key,s);
                hashMap.put(idKey,marks);
            }

        });
        //Log.i("aaaaa",s);


        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitMarks();
                context.startActivity(new Intent(context,AssignMarks.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView studentID,space;
        EditText mark;
        Button submit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            studentID = itemView.findViewById(R.id.studentID);
            space = itemView.findViewById(R.id.space);
            mark = itemView.findViewById(R.id.mark);
            submit = itemView.findViewById(R.id.submit);

        }
    }

    public void submitMarks(){
        String year,semester,dept,courseCode;
        courseCode = AssignMarks.courseCode;
        dept = AssignMarks.dept;
        year = AssignMarks.year;
        semester = AssignMarks.semester;
        DatabaseReference student = FirebaseDatabase.getInstance().getReference().child("Students").child(dept).child(year).child(semester);

        for (String id : hashMap.keySet()){
            for (String type : hashMap.get(id).keySet()){
                if(id.equals("1"))  continue;
                student.child(id).child(courseCode+type).setValue(hashMap.get(id).get(type));
            }
        }
    }

}
