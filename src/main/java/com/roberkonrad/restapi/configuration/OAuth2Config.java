package com.roberkonrad.restapi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Config {

    private String login;
    private String password;
    private String signingKey;
    private String clientId;
    private String secret;
    private String passwordGrantType;
    private int tokenValiditySeconds;
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

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPasswordGrantType() {
        return passwordGrantType;
    }

    public void setPasswordGrantType(String passwordGrantType) {
        this.passwordGrantType = passwordGrantType;
    }

    public int getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    public String[] getScopes() {
        return scopes.toArray(new String[0]);
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}
