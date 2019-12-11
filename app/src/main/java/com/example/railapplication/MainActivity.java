package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
            String name = trainName_edt.getText().toString().trim();
            String type = trainType_edt.getText().toString().trim();
            String atime = arrivalTime_edt.getText().toString().trim();
            String dtime = departTime_edt.getText().toString().trim();
            String coaches = numberOfCoaches_edt.getText().toString().trim();
            if(TextUtils.isEmpty(name)||
                    TextUtils.isEmpty(type)||
                    TextUtils.isEmpty(atime)||
                    TextUtils.isEmpty(dtime)||
                    TextUtils.isEmpty(coaches)){
                Toast.makeText(getApplicationContext(),"Required Fields Are Missing!!",Toast.LENGTH_LONG).show();
            }
            else {
                new MyTask(getApplicationContext(),name, type, atime, dtime, coaches).execute();
            }
        }
        else if(view==view_list_btn){
            startActivity(new Intent(MainActivity.this,ListOfTrains_Recycle_Activity.class));
        }
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String name,type,atime,dtime,coaches;
        Context context;

        public MyTask(Context context,String name, String type, String atime, String dtime, String coaches) {
            this.context = context;
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

                url = new URL("http://"+getResources().getString(R.string.id_address)+":8080/RailApplication/cegep/mobile/registerTrain&"+name+"&"+coaches+"&"+type+"&"+atime+"&"+dtime);

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
                    startActivity(new Intent(MainActivity.this,ListOfTrains_Recycle_Activity.class));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }
}
