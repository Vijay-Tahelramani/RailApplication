package com.example.railapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Train_All_List_Activity extends AppCompatActivity {

    ArrayList<train_data> trainDataArrayList;
    RecyclerView train_list_recyclerView;
    RecAdapter recAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_all_list);

        train_list_recyclerView = findViewById(R.id.Train_All_List_recycler_view);

        trainDataArrayList = new ArrayList<train_data>();
        recAdapter = new RecAdapter(trainDataArrayList,getApplicationContext());
        train_list_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        train_list_recyclerView.setAdapter(recAdapter);
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

                url = new URL("http://192.168.100.101:8080/RailApplication/cegep/mobile/viewCorrespondingStations");

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
                final JSONArray jsonArray = responseObj.getJSONArray("Trains");
                for(int i=0;i<jsonArray.length();i++)
                {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                    train_data item = new train_data(jsonObject.getInt("Train_id"),
                            jsonObject.getString("Train_name"),
                            jsonObject.getString("Train_type"),
                            jsonObject.getString("Arrival_time"),
                            jsonObject.getString("Depart_time"),
                            jsonObject.getInt("NumberOFCoaches"));
                    trainDataArrayList.add(item);
                    recAdapter.notifyDataSetChanged();
                    recAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();
                            String Routes = "";
                            try {
                                if(jsonArray.getJSONObject(position).has("Routes")) {
                                    Routes = jsonArray.getJSONObject(position).getJSONArray("Routes").toString();
                                    Intent i = new Intent(Train_All_List_Activity.this,RouteListActivity.class);
                                    i.putExtra("Routes",Routes);
                                    startActivity(i);
                                }
                                else {
                                    Toast.makeText(context,"No Routes Found",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
