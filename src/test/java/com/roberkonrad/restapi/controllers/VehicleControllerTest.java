package com.roberkonrad.restapi.controllers;

import com.roberkonrad.restapi.configuration.OAuth2Helper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OAuth2Helper oAuth2Helper;

    @Test
    public void getVehiclesByCoordinatesAndDistanceTest() throws Exception {
        Double lat = 53.9037654770889, lon = 20.887423009119, dist = 50.0;
        String accessToken = oAuth2Helper.obtainAccessToken();
        mockMvc.perform(get("/vehicles")
                .header("Authorization", "Bearer " + accessToken)
                .param("lat", String.valueOf(lat))
                .param("lon", String.valueOf(lon))
                .param("dist", String.valueOf(dist)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
