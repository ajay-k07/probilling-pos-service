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

import com.hdmdmi.probilling.pos.Tenant.TenantContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // if (request.getServletPath().contains("/v3/api-docs") ||
        // request.getServletPath().contains("/swagger-ui")) {
        // filterChain.doFilter(request, response);
        // return;
        // }

        try {

            log.info("Extracting JWT {}", request.getHeader("Authorization"));
            String token = extractToken(request);

            if (token != null && jwtTokenUtil.validateToken(token)) {

                Claims claims = jwtTokenUtil.getClaims(token);

                // Extract the roles from the claims
                List<String> roles = jwtTokenUtil.extractRoles(claims);
                System.out.println(roles.toString());
                // Perform authentication based on the roles
                Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                TenantContext.setCurrentTenant(String.valueOf(claims.get("tenantId")));

                // Continue processing the request

                log.info("User Authenticated Using JWT");

            } else {
                log.info("JWT Validation Failed");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + e.getMessage());
        }

        filterChain.doFilter(request, response);

    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}