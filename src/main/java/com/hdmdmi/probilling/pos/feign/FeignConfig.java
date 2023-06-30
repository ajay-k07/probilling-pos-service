package com.hdmdmi.probilling.pos.feign;

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

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Autowired
    private HttpServletRequest request;

    @Bean
    public RequestInterceptor forwardHeadersInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Forward headers from the incoming request
                if (request != null) {
                    String HttpRequestHeader;
                    template.header("Authorization", request.getHeader("Authorization"));
                }
            }
        };
    }
}
