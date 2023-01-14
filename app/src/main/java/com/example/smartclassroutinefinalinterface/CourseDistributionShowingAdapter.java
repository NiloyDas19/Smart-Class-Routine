package com.example.smartclassroutinefinalinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseDistributionShowingAdapter extends RecyclerView.Adapter<CourseDistributionShowingAdapter.MyViewHolder> {

    Context context;
    ArrayList<DistributionClass> distributionClassArrayList;

    public CourseDistributionShowingAdapter(Context context, ArrayList<DistributionClass> distributionClassArrayList) {
        this.context = context;
        this.distributionClassArrayList = distributionClassArrayList;
    }

    @NonNull
    @Override
    public CourseDistributionShowingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_course_distribution,parent,false);
        return new CourseDistributionShowingAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDistributionShowingAdapter.MyViewHolder holder, int position) {
        DistributionClass distributionClass = distributionClassArrayList.get(position);

        holder.teacherID.setText(distributionClass.getTeacherId());
        holder.code.setText(distributionClass.getCourseCode());
        holder.credit.setText(distributionClass.getCredit());
        holder.type.setText(distributionClass.getType());
        holder.year.setText(distributionClass.getYear());
    }

    @Override
    public int getItemCount() {
        return distributionClassArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView code,credit,type,year,teacherID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            teacherID = itemView.findViewById(R.id.teacherID);
            code = itemView.findViewById(R.id.courseCode);
            credit = itemView.findViewById(R.id.credit);
            type = itemView.findViewById(R.id.type);
            year = itemView.findViewById(R.id.year);
        }
    }

}

