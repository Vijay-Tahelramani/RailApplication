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

public class Stations_List_Activity extends AppCompatActivity {

    String Stations = "";
    ArrayList<station_data> stationDataArrayList;
    RecyclerView station_list_recyclerView;
    StationRecAdapter stationRecAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_list);

        station_list_recyclerView = findViewById(R.id.Stations_List_recycler_view);

        stationDataArrayList = new ArrayList<station_data>();
        stationRecAdapter = new StationRecAdapter(stationDataArrayList, getApplicationContext());
        station_list_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        station_list_recyclerView.setAdapter(stationRecAdapter);

        if (getIntent() != null) {
            Stations = getIntent().getStringExtra("Stations");
        }
        loadData();

    }

    private void loadData() {
        try {
            JSONArray jsonArray = new JSONArray(Stations);
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject jsonObject = jsonArray.getJSONObject(i);

                station_data item = new station_data(jsonObject.getInt("Station_id"),
                        jsonObject.getString("Contact_number"),
                        jsonObject.getString("Station_name"));
                stationDataArrayList.add(item);
                stationRecAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
