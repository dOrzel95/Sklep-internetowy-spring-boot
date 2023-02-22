package com.example.MontanaShop.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConf(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

                http.csrf().disable();
                http.cors().and();
                http.authorizeRequests()
                        .antMatchers("montanashop/login").permitAll().antMatchers("montanashop/register").permitAll()
                        .antMatchers("montanashop/login").permitAll().antMatchers("montanashop/client/home").authenticated()
                        .antMatchers("/montanashop/product/all").permitAll()
                        .antMatchers("/montanashop/positionshopping/all-by-shoppingcart").authenticated()
                        .antMatchers("/montanashop/positionshopping/add").permitAll()
                        .antMatchers("/montanashop/category/all").permitAll()
                        .antMatchers("/montanashop/upload").authenticated()
                        .antMatchers("/montanashop/product/add").permitAll()
                        .antMatchers("/montanashop/positionshopping/delete-all").authenticated()
                        .antMatchers("/montanashop/positionshopping/quantity-by-shoppingcart").authenticated()
                        .antMatchers("/montanashop/positionshopping/all-by-category").authenticated()
                        .antMatchers("/montanashop/product/update-all").permitAll()
                        .antMatchers("/montanashop/product/all-by-category-and-name").authenticated()
                        .antMatchers("/montanashop/product/all-by-name").authenticated()
                        .antMatchers("/montanashop/client/all-other-users").authenticated()
                        .antMatchers("/montanashop/client/delete/*").authenticated()
//                        .hasAuthority("[ROLE_ADMIN]")
                        .antMatchers("/montanashop/client/block-account").authenticated()
//                        .hasAuthority("[ROLE_ADMIN]")
                        .antMatchers("/montanashop/client/unlock-account").authenticated();
//                        .hasAuthority("[ROLE_ADMIN]")
//                        .and().addFilter(new JwtFilter(authenticationManager()));



    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
