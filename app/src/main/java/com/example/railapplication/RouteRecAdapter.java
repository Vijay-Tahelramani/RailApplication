package com.example.railapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RouteRecAdapter extends RecyclerView.Adapter<RouteRecAdapter.ViewHolder> {

    private ArrayList<route_data> routeDataList;
    private Context context;
    private View.OnClickListener RouteRecAdapterListener;

    public RouteRecAdapter(ArrayList<route_data> routeDataList, Context context) {
        this.routeDataList = routeDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public RouteRecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_items, parent, false);

        return new RouteRecAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteRecAdapter.ViewHolder holder, int position) {

        holder.route_source.setText("Source of Route: "+routeDataList.get(position).getSOURCE());
        holder.route_destination.setText("Destination: "+routeDataList.get(position).getDESTINATION());
        holder.route_date.setText("Date: "+routeDataList.get(position).getDATE_OF_ROUTE());

    }

    public void setOnClickListener(View.OnClickListener clickListener) {

        RouteRecAdapterListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return routeDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView route_source,route_destination,route_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            route_source = itemView.findViewById(R.id.route_source);
            route_destination = itemView.findViewById(R.id.route_destination);
            route_date = itemView.findViewById(R.id.route_date);

            itemView.setTag(this);

            itemView.setOnClickListener(RouteRecAdapterListener);

        }
    }


}
