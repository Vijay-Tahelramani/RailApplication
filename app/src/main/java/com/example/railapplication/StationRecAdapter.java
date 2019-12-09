package com.example.railapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StationRecAdapter extends RecyclerView.Adapter<StationRecAdapter.ViewHolder> {

    private ArrayList<station_data> stationDataList;
    private Context context;
    private View.OnClickListener StationRecAdapterListener;

    public StationRecAdapter(ArrayList<station_data> stationDataList, Context context) {
        this.stationDataList = stationDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public StationRecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_items, parent, false);

        return new StationRecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationRecAdapter.ViewHolder holder, int position) {

        holder.station_name.setText("Station Name: "+stationDataList.get(position).getStation_name());
        holder.station_contact.setText("Contact Number: "+stationDataList.get(position).getContact_number());




    }

    public void setOnClickListener(View.OnClickListener clickListener) {

        StationRecAdapterListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return stationDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView station_name,station_contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            station_name = itemView.findViewById(R.id.station_name);
            station_contact = itemView.findViewById(R.id.station_contact);

            itemView.setTag(this);

            itemView.setOnClickListener(StationRecAdapterListener);

        }
    }


}
