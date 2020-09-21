package com.roberkonrad.restapi.controller;

import com.roberkonrad.restapi.configuration.YAMLConfig;
import com.roberkonrad.restapi.model.Vehicle;
import com.roberkonrad.restapi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private YAMLConfig yamlConfig;

    @GetMapping
    public Map<String, Object> getVehiclesByCoordinatesAndDistance(@RequestParam double lat, @RequestParam double lon, @RequestParam double dist) {
//        http://localhost:8080/vehicles?lat=53.9037654770889&lon=20.887423009119&dist=500
        Map<String, Object> results = new HashMap<String, Object>();
        List<Vehicle> vehicles = vehicleService.getVehiclesByDistanceFromTarget(vehicleService.setUpData(), lat, lon, dist);
        results.put("amount", vehicles.size());
        results.put("vehicles", vehicles);
        return results;
    }
}
