package com.example.MontanaShop.AuthorizationTokenns;

import com.example.MontanaShop.Model.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class RefreshToken {
    String refreshToken;
    Claims claims;

    public RefreshToken(String refreshToken, Claims claims) {
        this.refreshToken = refreshToken;
        this.claims = claims;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }
}
