package com.example.railapplication;

public class passenger_data {
    int passenger_id;
    String first_name,last_name,address,phone;

    public passenger_data(int passenger_id, String first_name, String last_name, String address, String phone) {
        this.passenger_id = passenger_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone = phone;
    }

    public int getPassenger_id() {
        return passenger_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
