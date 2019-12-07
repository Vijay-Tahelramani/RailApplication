package com.example.railapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    private ArrayList<train_data> trainDataList;
    private Context context;
    private View.OnClickListener RecAdapterListener;

    public RecAdapter(ArrayList<train_data> trainDataList, Context context) {
        this.trainDataList = trainDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_list_items, parent, false);

        return new RecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, int position) {

        holder.train_name.setText("Train Name: "+trainDataList.get(position).getTrain_name());
        holder.train_type.setText("Train Type: "+trainDataList.get(position).getTrain_type());
        holder.arrival_time.setText("Arrival: "+trainDataList.get(position).getTrain_arrival_time());
        holder.departure_time.setText("Departure: "+trainDataList.get(position).getTrain_departure_time());
        holder.num_of_coach.setText("Number of coaches: "+trainDataList.get(position).getTrain_num_of_coaches());


    }

    public void setOnClickListener(View.OnClickListener clickListener) {

        RecAdapterListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return trainDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView train_name,train_type,num_of_coach,departure_time,arrival_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            train_name = itemView.findViewById(R.id.train_name);
            train_type = itemView.findViewById(R.id.train_type);
            num_of_coach = itemView.findViewById(R.id.num_of_coach);
            departure_time = itemView.findViewById(R.id.departure_time);
            arrival_time = itemView.findViewById(R.id.arrival_time);

            itemView.setTag(this);

            itemView.setOnClickListener(RecAdapterListener);

        }
    }


}
