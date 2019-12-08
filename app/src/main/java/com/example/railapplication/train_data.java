package com.example.railapplication;

public class train_data {

    String train_name,train_type,train_arrival_time,train_departure_time;
    int train_id,train_num_of_coaches;
    public train_data(int train_id, String train_name, String train_type, String train_arrival_time, String train_departure_time, int train_num_of_coaches) {
        this.train_id = train_id;
        this.train_name = train_name;
        this.train_type = train_type;
        this.train_arrival_time = train_arrival_time;
        this.train_departure_time = train_departure_time;
        this.train_num_of_coaches = train_num_of_coaches;
    }

    public int getTrain_id() {
        return train_id;
    }

    public String getTrain_name() {
        return train_name;
    }

    public String getTrain_type() {
        return train_type;
    }

    public String getTrain_arrival_time() {
        return train_arrival_time;
    }

    public String getTrain_departure_time() {
        return train_departure_time;
    }

    public int getTrain_num_of_coaches() {
        return train_num_of_coaches;
    }
}
