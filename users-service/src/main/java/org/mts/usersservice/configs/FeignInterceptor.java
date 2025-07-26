package org.mts.usersservice.configs;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class FeignInterceptor implements RequestInterceptor {
    /**
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {

        SecurityContext context = SecurityContextHolder.getContext();
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) context.getAuthentication();
        String jwtAccessToken = authentication.getToken().getTokenValue();
        requestTemplate.header("Authorization", "Bearer "+jwtAccessToken);
    }
}
