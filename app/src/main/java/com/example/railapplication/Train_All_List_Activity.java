package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

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

public class Train_All_List_Activity extends AppCompatActivity {

    ArrayList<train_data> trainDataArrayList;
    RecyclerView recyclerView;
    RecAdapter recAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_all_list);

        recyclerView = findViewById(R.id.Train_All_List_recycler_view);

        trainDataArrayList = new ArrayList<train_data>();
        recAdapter = new RecAdapter(trainDataArrayList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recAdapter);
        loadData();

    }

    public void loadData(){
        MyTask process = new MyTask();
        process.execute();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://192.168.3.102:8080/RailApplication/cegep/mobile/viewTrainsDetails");

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
                JSONArray jsonArray = responseObj.getJSONArray("Trains");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    train_data item = new train_data(jsonObject.getInt("TrainId"),
                            jsonObject.getString("TrainName"),
                            jsonObject.getString("TrainType"),
                            jsonObject.getString("ArrivalTime"),
                            jsonObject.getString("DepartTime"),
                            jsonObject.getInt("NumberOFCoaches"));
                    trainDataArrayList.add(item);
                    recAdapter.notifyDataSetChanged();
                    recAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();
                            Intent i = new Intent(Train_All_List_Activity.this,Update_trains_Activity.class);
                            i.putExtra("train_id",trainDataArrayList.get(position).getTrain_id());
                            i.putExtra("train_name",trainDataArrayList.get(position).getTrain_name());
                            i.putExtra("train_type",trainDataArrayList.get(position).getTrain_type());
                            i.putExtra("train_arrival_time",trainDataArrayList.get(position).getTrain_arrival_time());
                            i.putExtra("train_departure_time",trainDataArrayList.get(position).getTrain_departure_time());
                            i.putExtra("train_num_of_coaches",trainDataArrayList.get(position).getTrain_num_of_coaches());
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
