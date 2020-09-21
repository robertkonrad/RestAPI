package com.roberkonrad.restapi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
    private String login;
    private String password;
    private String signing_key;
    private String client_id;
    private String secret;
    private String password_grant_type;
    private int token_validity_seconds;
    private List<String> scopes;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSigning_key() {
        return signing_key;
    }

    public void setSigning_key(String signing_key) {
        this.signing_key = signing_key;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPassword_grant_type() {
        return password_grant_type;
    }

    public void setPassword_grant_type(String password_grant_type) {
        this.password_grant_type = password_grant_type;
    }

    public int getToken_validity_seconds() {
        return token_validity_seconds;
    }

    public void setToken_validity_seconds(int token_validity_seconds) {
        this.token_validity_seconds = token_validity_seconds;
    }

    public String[] getScopes() {
        return scopes.toArray(new String[0]);
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}
