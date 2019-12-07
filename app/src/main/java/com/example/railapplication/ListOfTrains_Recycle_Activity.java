package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListOfTrains_Recycle_Activity extends AppCompatActivity {

    ArrayList<train_data> trainDataArrayList;
    RecyclerView recyclerView;
    RecAdapter recAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_trains_recycle);

        recyclerView = findViewById(R.id.Train_List_recycler_view);

        trainDataArrayList = new ArrayList<train_data>();
        recAdapter = new RecAdapter(trainDataArrayList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recAdapter);

        trainDataArrayList.add(new train_data("1","Express","Metro","20 PM","9 AM","15"));
        trainDataArrayList.add(new train_data("2","Lotto","Metro","20 PM","9 AM","15"));
        trainDataArrayList.add(new train_data("3","Max","Metro","20 PM","9 AM","15"));
        trainDataArrayList.add(new train_data("4","Sgfh","Metro","20 PM","9 AM","15"));
        recAdapter.notifyDataSetChanged();
        recAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                Intent i = new Intent(ListOfTrains_Recycle_Activity.this,Update_trains_Activity.class);
                i.putExtra("train_id",trainDataArrayList.get(position).getTrain_id());
                i.putExtra("train_name",trainDataArrayList.get(position).getTrain_name());
                i.putExtra("train_type",trainDataArrayList.get(position).getTrain_type());
                i.putExtra("train_arrival_time",trainDataArrayList.get(position).getTrain_arrival_time());
                i.putExtra("train_departure_time",trainDataArrayList.get(position).getTrain_departure_time());
                i.putExtra("train_num_of_coaches",trainDataArrayList.get(position).getTrain_num_of_coaches());
                startActivity(i);
            }
        });

    }
}
