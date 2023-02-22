package com.example.MontanaShop.Service;

import com.example.MontanaShop.AuthorizationTokenns.AccesToken;
import com.example.MontanaShop.AuthorizationTokenns.RefreshToken;
import com.example.MontanaShop.Exception.RefreshTokenExpiredException;
import com.example.MontanaShop.Model.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class JwTokenManager {
    AccesToken accesToken;
    RefreshToken refreshToken;
    String refreshTokenKey;
    String accesTokenKey;
    ClientManager clientManager;
    Client verifyClient;

    @Autowired
    public JwTokenManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public String getRefreshTokenKey() {
        return refreshTokenKey;
    }

    public void setRefreshTokenKey(String refreshTokenKey) {
        this.refreshTokenKey = refreshTokenKey;
    }

    public String getAccesTokenKey() {
        return accesTokenKey;
    }

    public void setAccesTokenKey(String accesTokenKey) {
        this.accesTokenKey = accesTokenKey;
    }

    public AccesToken getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(AccesToken accesToken) {
        this.accesToken = accesToken;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
    public RefreshToken createRefreshToken(Client client){

        long now = System.currentTimeMillis();
        String clientId = new StringBuilder().append(client.getId()).toString();


        setRefreshTokenKey(UUID.randomUUID().toString());
        Claims claims = Jwts.claims().setSubject(client.getUsername());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(client.getUsername())
                .setId(clientId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("refreshToken"))
                .compact();
        return new RefreshToken(token, claims);
    }
    public AccesToken createAccesToken(Client client){
        long now = System.currentTimeMillis();
        long addTime = 24*10*60*60*1000;
        setAccesTokenKey(UUID.randomUUID().toString());
        HashMap<String, Object> claims = new ManagedMap<>();
        System.out.println(client.getAuthorities());
        String authority = client.getAuthorities().toString();

        claims.put("role", authority);
        claims.put("name", client.getUsername());
        String clientId = new StringBuilder().append(client.getId()).toString();

        String token = Jwts.builder().
                setClaims(claims).
                setSubject(client.getUsername())
                .setId(clientId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (30*60*1000)))
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("keySecret")).compact();
        return new AccesToken(token, claims);
    }
    public Client verifyRefreshToken(String token){
        Client client = null;
        String verifyToken =  token.substring(7);
        Jws<Claims> claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("refreshToken")).parseClaimsJws(verifyToken);
        String subject = claims.getBody().getSubject();
        String id = claims.getBody().getId();


        if (new Date(System.currentTimeMillis()).getTime()>claims.getBody().getExpiration().getTime()-1000) {
            throw new RefreshTokenExpiredException();
        }

        if (clientManager.existByUserName(subject) && (clientManager.existById(Long.parseLong(id)))){
            client = clientManager.findByUserName(subject);
        }
        return client;
    }
    public Client getVerifyClient() {
        return verifyClient;
    }

    public void setVerifyClient(Client verifyClient) {
        this.verifyClient = verifyClient;
    }
}
