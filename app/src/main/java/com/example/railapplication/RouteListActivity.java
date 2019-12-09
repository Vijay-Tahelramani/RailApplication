package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RouteListActivity extends AppCompatActivity {

    String Routes = "";
    ArrayList<route_data> routeDataArrayList;
    RecyclerView route_list_recyclerView;
    RouteRecAdapter routeRecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        route_list_recyclerView = findViewById(R.id.Route_List_recycler_view);

        routeDataArrayList = new ArrayList<route_data>();
        routeRecAdapter = new RouteRecAdapter(routeDataArrayList, getApplicationContext());
        route_list_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        route_list_recyclerView.setAdapter(routeRecAdapter);

        if (getIntent() != null) {
            Routes = getIntent().getStringExtra("Routes");
        }
        loadData();


    }

    private void loadData() {
        try {
            JSONArray jsonArray = new JSONArray(Routes);
            int len = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Corresponding_stations = "";
                if (jsonObject.has("Corresponding_stations")) {
                    Corresponding_stations = jsonObject.getString("Corresponding_stations");
                }
                route_data item = new route_data(jsonObject.getInt("ROUTE_ID"),
                        jsonObject.getString("SOURCE"),
                        jsonObject.getString("DESTINATION"),
                        jsonObject.getString("DATE_OF_ROUTE"),
                        Corresponding_stations);
                routeDataArrayList.add(item);
                routeRecAdapter.notifyDataSetChanged();
                routeRecAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                        int position = viewHolder.getAdapterPosition();
                        if (!routeDataArrayList.get(position).getSTATIONS().equals("")) {
                            Intent i = new Intent(RouteListActivity.this, Stations_List_Activity.class);
                            i.putExtra("Stations", routeDataArrayList.get(position).getSTATIONS());
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Stations Found", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
