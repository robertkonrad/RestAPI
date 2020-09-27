package com.roberkonrad.restapi.controller;

import com.roberkonrad.restapi.model.Vehicle;
import com.roberkonrad.restapi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public Map<String, Object> getVehiclesByCoordinatesAndDistance(@RequestParam double lat, @RequestParam double lon, @RequestParam double dist) {
        Map<String, Object> results = new HashMap<String, Object>();
        List<Vehicle> vehicles = vehicleService.getVehiclesByDistanceFromTarget(vehicleService.setUpData(), lat, lon, dist);
        results.put("amount", vehicles.size());
        results.put("vehicles", vehicles);
        return results;
    }
}
