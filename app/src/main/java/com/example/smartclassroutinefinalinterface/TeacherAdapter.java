package com.example.smartclassroutinefinalinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> {

    Context context;
    ArrayList<Teachers> teachersList;

    public TeacherAdapter(Context context, ArrayList<Teachers> teachersList) {
        this.context = context;
        this.teachersList = teachersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_item_teacher,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Teachers teachers = teachersList.get(position);
        holder.name.setText(teachers.getName());
        holder.id.setText(teachers.getId());
        holder.dept.setText(teachers.getDept());

    }

    @Override
    public int getItemCount() {
        return teachersList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,id,dept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.TSname);
            id = itemView.findViewById(R.id.TSid);
            dept = itemView.findViewById(R.id.TSdept);
        }
    }

}
