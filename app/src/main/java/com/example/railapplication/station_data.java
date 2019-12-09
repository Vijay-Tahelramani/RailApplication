package com.example.railapplication;

public class station_data {
    int Station_id;
    String Contact_number,Station_name;

    public station_data(int station_id, String contact_number, String station_name) {
        Station_id = station_id;
        Contact_number = contact_number;
        Station_name = station_name;
    }

    public int getStation_id() {
        return Station_id;
    }

    public String getContact_number() {
        return Contact_number;
    }

    public String getStation_name() {
        return Station_name;
    }
}
