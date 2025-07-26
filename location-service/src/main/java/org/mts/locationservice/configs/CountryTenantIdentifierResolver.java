package org.mts.locationservice.configs;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CountryTenantIdentifierResolver implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {

    @Value("${app.countries.default}")
    private String defaultCountry;

    /**
     * @return
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        return CountryContext.getCountry() == null? this.defaultCountry:CountryContext.getCountry();
    }

    /**
     * @return
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    /**
     * @param hibernateProperties
     */
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
