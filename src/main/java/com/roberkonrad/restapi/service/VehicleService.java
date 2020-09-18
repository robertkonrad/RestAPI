package com.roberkonrad.restapi.service;

import com.roberkonrad.restapi.model.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> setUpData();
    List<Vehicle> getVehiclesByDistanceFromTarget(List<Vehicle> vehicles, double lat, double lon, double dist);
}
