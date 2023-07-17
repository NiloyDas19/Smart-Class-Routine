package com.example.smartclassroutinefinalinterface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartclassroutinefinalinterface.weekdays.DomainRow;

import java.util.List;

import de.codecrafters.tableview.TableView;


public class TableAdapter2 extends RecyclerView.Adapter<TableAdapter2.Viewholder> {
    List<List<DomainRow>> list;
    Context context;


    public TableAdapter2(Context context, List<List<DomainRow>> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override


    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.table_layout, parent, false);
        Viewholder vh = new Viewholder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int pos) {

        holder.ViewDay.setText(getDay(pos+1));
        if (pos==4)     holder.print.setVisibility(View.VISIBLE);

        holder._11.setText(list.get(pos).get(0)._1);
        holder._12.setText(list.get(pos).get(0)._2);
        holder._13.setText(list.get(pos).get(0)._3);
        holder._14.setText(list.get(pos).get(0)._4);
        holder._15.setText(list.get(pos).get(0)._5);
        holder._16.setText(list.get(pos).get(0)._6);
        holder._17.setText(list.get(pos).get(0)._7);
        holder._18.setText(list.get(pos).get(0)._8);
        holder._19.setText(list.get(pos).get(0)._9);

        holder._21.setText(list.get(pos).get(1)._1);
        holder._22.setText(list.get(pos).get(1)._2);
        holder._23.setText(list.get(pos).get(1)._3);
        holder._24.setText(list.get(pos).get(1)._4);
        holder._25.setText(list.get(pos).get(1)._5);
        holder._26.setText(list.get(pos).get(1)._6);
        holder._27.setText(list.get(pos).get(1)._7);
        holder._28.setText(list.get(pos).get(1)._8);
        holder._29.setText(list.get(pos).get(1)._9);

        holder._31.setText(list.get(pos).get(2)._1);
        holder._32.setText(list.get(pos).get(2)._2);
        holder._33.setText(list.get(pos).get(2)._3);
        holder._34.setText(list.get(pos).get(2)._4);
        holder._35.setText(list.get(pos).get(2)._5);
        holder._36.setText(list.get(pos).get(2)._6);
        holder._37.setText(list.get(pos).get(2)._7);
        holder._38.setText(list.get(pos).get(2)._8);
        holder._39.setText(list.get(pos).get(2)._9);

        holder._41.setText(list.get(pos).get(3)._1);
        holder._42.setText(list.get(pos).get(3)._2);
        holder._43.setText(list.get(pos).get(3)._3);
        holder._44.setText(list.get(pos).get(3)._4);
        holder._45.setText(list.get(pos).get(3)._5);
        holder._46.setText(list.get(pos).get(3)._6);
        holder._47.setText(list.get(pos).get(3)._7);
        holder._48.setText(list.get(pos).get(3)._8);
        holder._49.setText(list.get(pos).get(3)._9);

        holder.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = holder.tableView;
                Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bmp);
                v.draw(c);
                PrintHelper photoPrinter = new PrintHelper(context);
                photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                photoPrinter.printBitmap("Routine", bmp);
            }
        });

    }

    public String getDay(int i){
        String day = "Fri";
        if (i==1)           day = "Saturday";
        else if (i==2)      day = "Sunday";
        else if (i==3)      day = "Monday";
        else if (i==4)      day = "Tuesday";
        else if (i==5)      day = "Wednesday";

        return day;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView _11, _12, _13, _14, _15, _16, _17, _18, _19,
        _21, _22, _23, _24, _25, _26, _27, _28, _29,
        _31, _32, _33, _34, _35, _36, _37, _38, _39,
        _41, _42, _43, _44, _45, _46, _47, _48, _49;

        TextView ViewDay;

        Button print ;
        TableLayout tableView;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            ViewDay = itemView.findViewById(R.id.dayfind);
            print = itemView.findViewById(R.id.print);
            tableView = itemView.findViewById(R.id.tableLayout);

            //row 1
            _11 = itemView.findViewById(R.id.r11);
            _12 = itemView.findViewById(R.id.r12);
            _13 = itemView.findViewById(R.id.r13);
            _14 = itemView.findViewById(R.id.r14);
            _15 = itemView.findViewById(R.id.r15);
            _16 = itemView.findViewById(R.id.r6);
            _17 = itemView.findViewById(R.id.r17);
            _18 = itemView.findViewById(R.id.r18);
            _19 = itemView.findViewById(R.id.r19);

            //row 2
            _21 = itemView.findViewById(R.id.r21);
            _22 = itemView.findViewById(R.id.r22);
            _23 = itemView.findViewById(R.id.r23);
            _24 = itemView.findViewById(R.id.r24);
            _25 = itemView.findViewById(R.id.r25);
            _26 = itemView.findViewById(R.id.r26);
            _27 = itemView.findViewById(R.id.r27);
            _28 = itemView.findViewById(R.id.r28);
            _29 = itemView.findViewById(R.id.r29);

            //row 3
            _31 = itemView.findViewById(R.id.r31);
            _32 = itemView.findViewById(R.id.r32);
            _33 = itemView.findViewById(R.id.r33);
            _34 = itemView.findViewById(R.id.r34);
            _35 = itemView.findViewById(R.id.r35);
            _36 = itemView.findViewById(R.id.r36);
            _37 = itemView.findViewById(R.id.r37);
            _38 = itemView.findViewById(R.id.r38);
            _39 = itemView.findViewById(R.id.r39);

            //row 4
            _41 = itemView.findViewById(R.id.r41);
            _42 = itemView.findViewById(R.id.r42);
            _43 = itemView.findViewById(R.id.r43);
            _44 = itemView.findViewById(R.id.r44);
            _45 = itemView.findViewById(R.id.r45);
            _46 = itemView.findViewById(R.id.r46);
            _47 = itemView.findViewById(R.id.r47);
            _48 = itemView.findViewById(R.id.r48);
            _49 = itemView.findViewById(R.id.r49);

        }
    }

    //void printHelper(View view){
//        PrintManager printManager = (PrintManager) context.getSystemService(context.PRINT_SERVICE);
//        PrintDocumentAdapter printDocumentAdapter = null;
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            printDocumentAdapter =
//        }


    //}
}
