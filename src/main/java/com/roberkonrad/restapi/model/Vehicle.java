package com.roberkonrad.restapi.model;

public class Vehicle {
    String position_id;
    double latitude;
    double longitude;

    public Vehicle() {
    }

    public Vehicle(String position_id, double latitude, double longitude) {
        this.position_id = position_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPosition_id() {
        return position_id;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "position_id='" + position_id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
