package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Update_trains_Activity extends AppCompatActivity implements View.OnClickListener {

    Button update_submit_btn;
    EditText update_trainName_edt,update_numberOfCoaches_edt,update_trainType_edt,update_arrivalTime_edt,update_departTime_edt;
    int train_id = 0;
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
            train_id = getIntent().getIntExtra("train_id",0);
            update_trainName_edt.setText(getIntent().getStringExtra("train_name"));
            update_numberOfCoaches_edt.setText(String.valueOf(getIntent().getIntExtra("train_num_of_coaches",0)));
            update_trainType_edt.setText(getIntent().getStringExtra("train_type"));
            update_arrivalTime_edt.setText(getIntent().getStringExtra("train_arrival_time"));
            update_departTime_edt.setText(getIntent().getStringExtra("train_departure_time"));
        }
    }

    @Override
    public void onClick(View view) {
        if(view==update_submit_btn){
            String name = update_trainName_edt.getText().toString().trim();
            String type = update_trainType_edt.getText().toString().trim();
            String atime = update_arrivalTime_edt.getText().toString().trim();
            String dtime = update_departTime_edt.getText().toString().trim();
            String coaches = update_numberOfCoaches_edt.getText().toString().trim();
            if(TextUtils.isEmpty(name)||
                    TextUtils.isEmpty(type)||
                    TextUtils.isEmpty(atime)||
                    TextUtils.isEmpty(dtime)||
                    TextUtils.isEmpty(coaches)){
                Toast.makeText(getApplicationContext(),"Required Fields Are Missing!!",Toast.LENGTH_LONG).show();
            }
            else {
                new MyTask(getApplicationContext(),String.valueOf(train_id),name, type, atime, dtime, coaches).execute();
            }
        }
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String id,name,type,atime,dtime,coaches;
        Context context;

        public MyTask(Context context,String id,String name, String type, String atime, String dtime, String coaches) {
            this.context = context;
            this.id = id;
            this.name = name;
            this.type = type;
            this.atime = atime;
            this.dtime = dtime;
            this.coaches = coaches;
        }

        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://"+getResources().getString(R.string.id_address)+":8080/RailApplication/cegep/mobile/modifyTrain&"+id+"&"+name+"&"+type+"&"+coaches+"&"+atime+"&"+dtime);

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();

                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);

                InputStreamReader myInput= new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                message = response.toString();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            return message;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            parseJsonData(result);
        }
        private void parseJsonData(String jsonResponse){
            try
            {
                JSONObject responseObj = new JSONObject(jsonResponse);
                Toast.makeText(context,responseObj.getString("message"),Toast.LENGTH_LONG).show();
                if(responseObj.getString("Status").equals("OK")){
                    startActivity(new Intent(Update_trains_Activity.this,ListOfTrains_Recycle_Activity.class));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }
}
