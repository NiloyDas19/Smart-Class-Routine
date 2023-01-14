package com.example.smartclassroutinefinalinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseShowingAdapter extends RecyclerView.Adapter<CourseShowingAdapter.MyViewHolder> {

    Context context;
    ArrayList<Course> courseList;

    public CourseShowingAdapter(Context context, ArrayList<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_item_course,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.title.setText(course.getTitle());
        holder.code.setText(course.getCode());
        holder.credit.setText(course.getCredit());
        holder.type.setText(course.getType());
        holder.year.setText(course.getYear());
        holder.semester.setText(course.getSemester());
        holder.dept.setText(course.getDept());

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,code,credit,type,year,semester,dept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.CStitle);
            code = itemView.findViewById(R.id.CScode);
            credit = itemView.findViewById(R.id.CScredit);
            type = itemView.findViewById(R.id.CStype);
            year = itemView.findViewById(R.id.CSyear);
            semester = itemView.findViewById(R.id.CSsemester);
            dept = itemView.findViewById(R.id.CSdepartment);
        }
    }

}
