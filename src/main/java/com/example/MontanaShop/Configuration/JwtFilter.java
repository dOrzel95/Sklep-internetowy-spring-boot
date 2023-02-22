package com.example.MontanaShop.Configuration;

import com.example.MontanaShop.Exception.AccesTokenExpiredException;
import com.example.MontanaShop.Service.JwTokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class JwtFilter extends BasicAuthenticationFilter {
    @Autowired
    private JwTokenManager manager;
    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    RequestMatcher customFilterUrl = new AntPathRequestMatcher("/montanashop/login");
    RequestMatcher customFilterUrl2 = new AntPathRequestMatcher("/montanashop/register");
    RequestMatcher customFilterUrl3 = new AntPathRequestMatcher("/montanashop/refreshtoken");
    RequestMatcher customFilterUrl4 = new AntPathRequestMatcher("/montanashop/activate-account/{tokenValue}");
    RequestMatcher customFilterUrl5 = new AntPathRequestMatcher("/montanashop/confirm");
    @Override
    protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        if (!customFilterUrl.matches(request)&&!customFilterUrl2.matches(request)&&!customFilterUrl3.matches(request)
                &&!customFilterUrl4.matches(request)&&!customFilterUrl5.matches(request)

        ){

            String header = request.getHeader("Authorization");


            try {
                UsernamePasswordAuthenticationToken authResult = getAutehenticationByToken(header);
                SecurityContextHolder.getContext().setAuthentication(authResult);
            }catch (AccesTokenExpiredException e){
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
            }

        }

        chain.doFilter(request,response);
    }
    private UsernamePasswordAuthenticationToken getAutehenticationByToken(String header){
        

        String token = header.substring(7);


        Jws<Claims> claims = null;
            try {
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("keySecret")).parseClaimsJws(token);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            if (new Date(System.currentTimeMillis()).getTime()>claims.getBody().getExpiration().getTime()-1000) {
                throw new AccesTokenExpiredException();
            }
        String username = claims.getBody().get("name").toString();

        String role = claims.getBody().get("role").toString();
        System.out.println(role);

        Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = Collections.singleton( new  SimpleGrantedAuthority(role));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthoritySet);
        return usernamePasswordAuthenticationToken;
    }
}
