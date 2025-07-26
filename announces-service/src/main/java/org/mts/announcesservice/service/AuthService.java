package org.mts.announcesservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements IAuthService {




    /**
     * @return
     */
    @Override
    public String getUserId() {

         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("sub");  // L'ID de l'utilisateur
        }

        return null;
    }
}
