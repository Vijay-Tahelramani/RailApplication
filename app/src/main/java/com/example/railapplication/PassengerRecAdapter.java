package com.example.railapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PassengerRecAdapter extends RecyclerView.Adapter<PassengerRecAdapter.ViewHolder> {

    private ArrayList<passenger_data> passengerDataList;
    private Context context;
    private View.OnClickListener PassengerRecAdapterListener;

    public PassengerRecAdapter(ArrayList<passenger_data> passengerDataList, Context context) {
        this.passengerDataList = passengerDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public PassengerRecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_items, parent, false);

        return new PassengerRecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerRecAdapter.ViewHolder holder, int position) {

        holder.passenger_name.setText(passengerDataList.get(position).getFirst_name()+" "+passengerDataList.get(position).getLast_name());


    }

    public void setOnClickListener(View.OnClickListener clickListener) {

        PassengerRecAdapterListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return passengerDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView passenger_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            passenger_name = itemView.findViewById(R.id.passenger_name);

            itemView.setTag(this);

            itemView.setOnClickListener(PassengerRecAdapterListener);

        }
    }


}
