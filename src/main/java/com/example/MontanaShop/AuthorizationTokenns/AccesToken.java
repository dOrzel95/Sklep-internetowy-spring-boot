package com.example.MontanaShop.AuthorizationTokenns;

import java.util.HashMap;

public class AccesToken {
    String token;
    HashMap<String,Object> claims;

    public AccesToken(String token, HashMap<String, Object> claims) {
        this.token = token;
        this.claims = claims;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HashMap<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(HashMap<String, Object> claims) {
        this.claims = claims;
    }
}
