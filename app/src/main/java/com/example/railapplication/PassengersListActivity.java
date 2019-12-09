package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class PassengersListActivity extends AppCompatActivity {

    RecyclerView Passengers_List_recycler_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers_list);
        Passengers_List_recycler_view = findViewById(R.id.Passengers_List_recycler_view);
    }
}
