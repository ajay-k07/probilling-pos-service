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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Base64.Decoder;

@Service
public class JwtTokenUtil {
    private static final String SECRET_KEY = "8EA51ABCA2A38A361EDD329F32D718EA51ABCA2A38A361EDD329F32D71";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public String generateToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Key getSigKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<String> extractRoles(Claims claims) {
        // Extract the roles from the claims
        // Modify this method based on how your JWT is structured
        List<String> roles = claims.get("roles", List.class);
        return roles != null ? roles : Collections.emptyList();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Claims getClaims(String token) {
        // Use a JWT library to validate the token based on the configured properties
        // For example, using jjwt library:
        return Jwts.parser()
                .setSigningKey(getSigKey()) // Replace with your secret key
                .parseClaimsJws(token)
                .getBody();
    }
}
