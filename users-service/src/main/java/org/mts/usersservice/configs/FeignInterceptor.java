package org.mts.usersservice.configs;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // ✅ Vérifier que l'authentification est bien de type JwtAuthenticationToken
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            String jwtAccessToken = jwtAuth.getToken().getTokenValue();

            if (jwtAccessToken != null && !jwtAccessToken.isEmpty()) {
                requestTemplate.header("Authorization", "Bearer " + jwtAccessToken);
            }
        }
    }

}
