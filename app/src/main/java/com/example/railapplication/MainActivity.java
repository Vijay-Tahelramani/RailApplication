package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button submit_btn,view_list_btn;
    EditText trainName_edt,numberOfCoaches_edt,trainType_edt,arrivalTime_edt,departTime_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_list_btn = findViewById(R.id.view_list_btn);
        submit_btn = findViewById(R.id.submit_btn);
        view_list_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        trainName_edt = findViewById(R.id.trainName_edt);
        numberOfCoaches_edt = findViewById(R.id.numberOfCoaches_edt);
        trainType_edt = findViewById(R.id.trainType_edt);
        arrivalTime_edt = findViewById(R.id.arrivalTime_edt);
        departTime_edt = findViewById(R.id.departTime_edt);

    }

    @Override
    public void onClick(View view) {
        if(view==submit_btn){

        }
        else if(view==view_list_btn){
            startActivity(new Intent(MainActivity.this,ListOfTrains_Recycle_Activity.class));
        }
    }
}
