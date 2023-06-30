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

package com.hdmdmi.probilling.pos.Tenant;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {
    private static final String UNKNOWN = "UNKNOWN";

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return Optional.ofNullable(TenantContext.getCurrentTenant()).orElse(UNKNOWN);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}
