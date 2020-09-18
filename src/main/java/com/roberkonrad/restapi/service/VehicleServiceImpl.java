package com.roberkonrad.restapi.service;

import com.roberkonrad.restapi.model.Vehicle;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final double rEarth = 6378.14;

    public double haversineDistanceInMetres(double lat1, double lon1, double lat2, double lon2) {
        return 2 * rEarth * Math.asin(Math.sqrt(Math.pow(Math.sin((lat2 - lat1) / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon2 - lon1) / 2), 2))) * 1000;
    }

    @Override
    public List<Vehicle> setUpData() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/static/csv/gps_pos.csv"));) {
            bufferedReader.readLine();
            String textLine = null;
            while ((textLine = bufferedReader.readLine()) != null) {
                String[] values = textLine.split(",");
                if (values.length < 3) {
                    continue;
                }
                Vehicle vehicle = new Vehicle();
                vehicle.setPosition_id(values[0]);
                vehicle.setLatitude(Double.parseDouble(values[1]));
                vehicle.setLongitude(Double.parseDouble(values[2]));
                vehicles.add(vehicle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByDistanceFromTarget(List<Vehicle> vehicles, double lat, double lon, double dist) {
//        Haversine formula
        List<Vehicle> results = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (haversineDistanceInMetres(Math.toRadians(lat), Math.toRadians(lon), Math.toRadians(vehicle.getLatitude()), Math.toRadians(vehicle.getLongitude())) <= dist) {
                results.add(vehicle);
            }
        }
        return results;
    }
}
