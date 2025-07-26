package org.mts.locationservice.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CountryContextFilter extends OncePerRequestFilter {

    @Value("${app.countries.default}")
    private String defaultCountry;

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
            country = this.defaultCountry;
        }
        CountryContext.setCountry(country.toLowerCase()); // Stocké en ThreadLocal

        try {
            filterChain.doFilter(request, response);
        } finally {
            CountryContext.clear(); // très important !
        }
    }
}
