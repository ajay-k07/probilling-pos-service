/*
 * Copyright (c)  Ajay K
 *
 *  All rights reserved.
 *
 *  This software is the property of Ajay K
 *  and contains proprietary and confidential information
 *  that is not to be reproduced or disclosed without
 *  prior written consent.
 *
 *  Unauthorized reproduction, distribution, or disclosure
 *  of this software, or any portion of it, may result in
 *  severe civil and criminal penalties, and will be
 *  prosecuted to the maximum extent possible under the law.
 *
 *
 */

package com.hdmdmi.probilling.pos.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // @Bean
    // protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    // http.csrf().disable()
    // .authorizeRequests()
    // .anyRequest().authenticated()
    // .and()
    // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    // .and()
    // .addFilterBefore(jwtRequestFilter,
    // UsernamePasswordAuthenticationFilter.class);
    // return http.build();
    // }
    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/proxy/**",
            "/webjars/**",

            "/v1/probilling/api/v1/auth/**",
            "/v1/probilling/v3/api-docs/**",
            "/v1/probilling/v3/api-docs.yaml",
            "/v1/probilling/swagger-ui/**",
            "/v1/probilling/swagger-ui.html",
            "/v1/probilling/swagger-resources/**",
            "/v1/probilling/proxy/**",
            "/v1/probilling/webjars/**"

    };
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                AUTH_WHITELIST);
    }
}
