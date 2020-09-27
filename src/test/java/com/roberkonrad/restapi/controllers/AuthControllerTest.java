package com.roberkonrad.restapi.controllers;

import com.roberkonrad.restapi.configuration.OAuth2Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class AuthControllerTest {

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
    public void getAccessTokenValidDataTest() throws Exception {
        mockMvc.perform(post("/oauth/token")
                .param("grant_type", oAuth2Config.getPasswordGrantType())
                .param("username", oAuth2Config.getLogin())
                .param("password", oAuth2Config.getPassword())
                .with(httpBasic(oAuth2Config.getClientId(), oAuth2Config.getSecret()))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("get-token-valid",
                        requestParameters(
                                parameterWithName("grant_type").description("Grant type to obtain access token."),
                                parameterWithName("username").description("User's name."),
                                parameterWithName("password").description("User's password.")),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic authentication.")),
                        responseFields(
                                fieldWithPath("access_token").description("Received access token."),
                                fieldWithPath("token_type").description("Type of token."),
                                fieldWithPath("expires_in").description("Token's expiry time."),
                                fieldWithPath("scope").description("The scope determines what the holder of token is allowed to do."),
                                fieldWithPath("jti").description("Random identificator, unique for every token."))));
    }

    @Test
    public void getAccessTokenInvalidDataTest() throws Exception {
        mockMvc.perform(post("/oauth/token")
                .param("grant_type", oAuth2Config.getPasswordGrantType())
                .param("username", "InvalidLogin")
                .param("password", "InvalidPassword")
                .with(httpBasic(oAuth2Config.getClientId(), oAuth2Config.getSecret()))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("get-token-invalid"));
    }

    @Test
    public void checkAccessTokenValidDataTest() throws Exception {
        mockMvc.perform(post("/oauth/check_token")
                .with(httpBasic(oAuth2Config.getClientId(), oAuth2Config.getSecret()))
                .param("token", getAccessToken()))
                .andExpect(status().isOk())
                .andDo(document("check-access-token-valid",
                        requestParameters(
                                parameterWithName("token").description("Token that will be check.")),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic authentication.")),
                        responseFields(
                                fieldWithPath("user_name").description("User's name."),
                                fieldWithPath("scope").description("The scope determines what the holder of token is allowed to do."),
                                fieldWithPath("active").description("Determine that user is active or not."),
                                fieldWithPath("exp").description("Identifies the expiration time on and after which the JWT must not be accepted for processing."),
                                fieldWithPath("authorities").description("Authorities of the user."),
                                fieldWithPath("jti").description("Case sensitive unique identifier of the token. "),
                                fieldWithPath("client_id").description("Client's identificator."))));
    }

    @Test
    public void checkAccessTokenInvalidDataTest() throws Exception {
        mockMvc.perform(post("/oauth/check_token")
                .with(httpBasic(oAuth2Config.getClientId(), "InvalidSecret"))
                .param("token", getAccessToken()))
                .andExpect(status().isUnauthorized())
                .andDo(document("check-access-token-invalid"));
    }
}
