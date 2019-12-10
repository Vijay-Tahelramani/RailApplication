package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import java.util.ArrayList;

public class PassengersListActivity extends AppCompatActivity {

    ArrayList<passenger_data> passengerDataArrayList;
    RecyclerView passenger_list_recyclerView;
    PassengerRecAdapter passengerRecAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers_list);
        passenger_list_recyclerView = findViewById(R.id.Passengers_List_recycler_view);

        passengerDataArrayList = new ArrayList<passenger_data>();
        passengerRecAdapter = new PassengerRecAdapter(passengerDataArrayList, getApplicationContext());
        passenger_list_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        passenger_list_recyclerView.setAdapter(passengerRecAdapter);

        loadData();
    }

    public void loadData(){
        MyTask process = new MyTask(getApplicationContext());
        process.execute();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        Context context;

        public MyTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://192.168.2.44:8080/RailApplication/cegep/mobile/viewPassengersDetails");

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
                final JSONArray jsonArray = responseObj.getJSONArray("Passengers");
                for(int i=0;i<jsonArray.length();i++)
                {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    passenger_data item = new passenger_data(jsonObject.getInt("passenger_id"),
                            jsonObject.getString("first_name"),
                            jsonObject.getString("last_name"),
                            jsonObject.getString("address"),
                            jsonObject.getString("phone"));
                    passengerDataArrayList.add(item);
                    passengerRecAdapter.notifyDataSetChanged();
                    passengerRecAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();
                            Intent i = new Intent(PassengersListActivity.this,PassengerDetailsActivity.class);
                            i.putExtra("passenger_id",passengerDataArrayList.get(position).getPassenger_id());
                            i.putExtra("first_name",passengerDataArrayList.get(position).getFirst_name());
                            i.putExtra("last_name",passengerDataArrayList.get(position).getLast_name());
                            i.putExtra("address",passengerDataArrayList.get(position).getAddress());
                            i.putExtra("phone",passengerDataArrayList.get(position).getPhone());
                            startActivity(i);
                        }
                    });

                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }
}
