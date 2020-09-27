package com.roberkonrad.restapi.controllers;

import com.roberkonrad.restapi.configuration.OAuth2Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {

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
    public void uploadFileWithAuthTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "exampleFile.data", "text/plain", "example".getBytes());
        mockMvc.perform(multipart("/uploadFile")
                .file(file)
                .header("Authorization", "Bearer " + getAccessToken()))
                .andExpect(status().isCreated());
    }

    @Test
    public void uploadFileWithoutAuthTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "exampleFile.data", "text/plain", "example".getBytes());
        mockMvc.perform(multipart("/uploadFile")
                .file(file))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void downloadFileWithAuthTest() throws Exception {
        mockMvc.perform(get("/downloadFile/exampleFile.data")
                .header("Authorization", "Bearer " + getAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void downloadFileWithAuthNotFoundTest() throws Exception {
        mockMvc.perform(get("/downloadFile/exampleFileNotFound.data")
                .header("Authorization", "Bearer " + getAccessToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void downloadFileWithoutAuthTest() throws Exception {
        mockMvc.perform(get("/downloadFile/exampleFile.data"))
                .andExpect(status().isUnauthorized());
    }
}
