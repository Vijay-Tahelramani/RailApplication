package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Update_trains_Activity extends AppCompatActivity implements View.OnClickListener {

    Button update_submit_btn;
    EditText update_trainName_edt,update_numberOfCoaches_edt,update_trainType_edt,update_arrivalTime_edt,update_departTime_edt;
    String train_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trains);

        update_submit_btn = findViewById(R.id.update_submit_btn);
        update_trainName_edt = findViewById(R.id.update_trainName_edt);
        update_numberOfCoaches_edt = findViewById(R.id.update_numberOfCoaches_edt);
        update_trainType_edt = findViewById(R.id.update_trainType_edt);
        update_arrivalTime_edt = findViewById(R.id.update_arrivalTime_edt);
        update_departTime_edt = findViewById(R.id.update_departTime_edt);

        update_submit_btn.setOnClickListener(this);

        if(getIntent()!=null){
            train_id = getIntent().getStringExtra("train_id");
            update_trainName_edt.setText(getIntent().getStringExtra("train_name"));
            update_numberOfCoaches_edt.setText(getIntent().getStringExtra("train_num_of_coaches"));
            update_trainType_edt.setText(getIntent().getStringExtra("train_type"));
            update_arrivalTime_edt.setText(getIntent().getStringExtra("train_arrival_time"));
            update_departTime_edt.setText(getIntent().getStringExtra("train_departure_time"));
        }
    }

    @Override
    public void onClick(View view) {
        if(view==update_submit_btn){

        }
    }
}
