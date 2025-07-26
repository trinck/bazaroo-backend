package org.mts.notificationservice.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mts.notificationservice.utilities.HeaderRemovingResponseWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


@Component
@Order(Integer.MAX_VALUE)
public class SockJsCleanupFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        HeaderRemovingResponseWrapper responseWrapper = new HeaderRemovingResponseWrapper(response);
        // Poursuit la chaîne
        filterChain.doFilter(request, responseWrapper);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // N'intervient que sur les requêtes SockJS (ajuste selon ta route)
        return !path.startsWith("/ws") && !path.contains("sockjs");
    }
}
