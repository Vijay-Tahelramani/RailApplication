package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class PassengerDetailsActivity extends AppCompatActivity {

    int passenger_id = 0;
    TextView passenger_fname,passenger_lname,passenger_address,passenger_phone;
    Button delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_details);
        passenger_fname = findViewById(R.id.passenger_fname);
        passenger_lname = findViewById(R.id.passenger_lname);
        passenger_address = findViewById(R.id.passenger_address);
        passenger_phone = findViewById(R.id.passenger_phone);
        delete_btn = findViewById(R.id.delete_btn);

        if(getIntent()!=null){
            passenger_id = getIntent().getIntExtra("passenger_id",0);
            passenger_lname.setText("First Name: "+getIntent().getStringExtra("first_name"));
            passenger_fname.setText("Last Name: "+getIntent().getStringExtra("last_name"));
            passenger_address.setText("Address: "+getIntent().getStringExtra("address"));
            passenger_phone.setText("Phone: "+getIntent().getStringExtra("phone"));
        }
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeletePassenger();
            }
        });

    }

    private void DeletePassenger(){
        MyTask process = new MyTask(getApplicationContext(),passenger_id);
        process.execute();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        Context context;
        int id;

        public MyTask(Context context,int id) {
            this.context = context;
            this.id = id;
        }

        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://192.168.3.102:8080/RailApplication/cegep/mobile/removePassenger&"+id);

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
                System.out.println("Response: " + responseObj);
                Toast.makeText(context,responseObj.getString("message"),Toast.LENGTH_LONG).show();
                if(responseObj.getString("Status").equals("OK")){
                    startActivity(new Intent(PassengerDetailsActivity.this,PassengersListActivity.class));
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }
}
