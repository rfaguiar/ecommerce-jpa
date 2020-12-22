package com.ecommerce.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class EcmCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static void setTenantIdentifier(String tenantId) {
        tl.set(tenantId);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return tl.get();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
