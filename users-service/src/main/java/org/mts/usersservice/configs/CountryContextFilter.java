package org.mts.usersservice.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CountryContextFilter extends OncePerRequestFilter {

    @Value("${app.countries.default}")
    private String defaultCountry;
    @Autowired
    private KeycloakProperties  keycloakProperties;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String country = request.getHeader("X-Country"); // ex: "fr.site.com"
        if (country == null){
            CountryContext.setCountry(this.keycloakProperties.getDefaultTenantId());
            CountryContext.setRealm(this.keycloakProperties.getDefaultRealm());

        }else {
            CountryContext.setRealm(this.keycloakProperties.getRealms().stream().filter(r->country.equalsIgnoreCase(r.getTenantId())).findFirst().get().getRealm());
            CountryContext.setCountry(country);
        }
         // Stocké en ThreadLocal
       // String tenantId = this.keycloakProperties.getRealms().stream().map(KeycloakProperties.RealmConfig::getTenantId).toList().stream().filter(s -> s.equalsIgnoreCase(country));

        try {
            filterChain.doFilter(request, response);
        } finally {
            CountryContext.clear(); // très important !
        }
    }
}
