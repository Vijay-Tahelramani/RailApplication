package com.example.railapplication;

public class route_data {
    int ROUTE_ID;
    String SOURCE,DESTINATION,DATE_OF_ROUTE,STATIONS;

    public route_data(int ROUTE_ID, String SOURCE, String DESTINATION, String DATE_OF_ROUTE, String STATIONS) {
        this.ROUTE_ID = ROUTE_ID;
        this.SOURCE = SOURCE;
        this.DESTINATION = DESTINATION;
        this.DATE_OF_ROUTE = DATE_OF_ROUTE;
        this.STATIONS = STATIONS;
    }

    public int getROUTE_ID() {
        return ROUTE_ID;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public String getDESTINATION() {
        return DESTINATION;
    }

    public String getDATE_OF_ROUTE() {
        return DATE_OF_ROUTE;
    }

    public String getSTATIONS() {
        return STATIONS;
    }
}
