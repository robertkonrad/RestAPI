package com.roberkonrad.restapi.controllers;

import com.roberkonrad.restapi.configuration.OAuth2Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OAuth2Config oAuth2Config;

    private String getAccessToken() throws Exception {
        ResultActions resultActions =
                mockMvc.perform(post("/oauth/token")
                        .param("grant_type", oAuth2Config.getPasswordGrantType())
                        .param("username", oAuth2Config.getLogin())
                        .param("password", oAuth2Config.getPassword())
                        .with(httpBasic(oAuth2Config.getClientId(), oAuth2Config.getSecret()))
                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("application/json;charset=UTF-8"));
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void getVehiclesByCoordinatesAndDistanceWithAuthTest() throws Exception {
        Double lat = 53.9037654770889, lon = 20.887423009119, dist = 50.0;
        String accessToken = getAccessToken();
        mockMvc.perform(get("/vehicles")
                .header("Authorization", "Bearer " + accessToken)
                .param("lat", String.valueOf(lat))
                .param("lon", String.valueOf(lon))
                .param("dist", String.valueOf(dist)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void getVehiclesByCoordinatesAndDistanceWithoutAuthTest() throws Exception {
        Double lat = 53.9037654770889, lon = 20.887423009119, dist = 50.0;
        mockMvc.perform(get("/vehicles")
                .param("lat", String.valueOf(lat))
                .param("lon", String.valueOf(lon))
                .param("dist", String.valueOf(dist)))
                .andExpect(status().isUnauthorized());
    }
}
