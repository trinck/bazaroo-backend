package org.mts.notificationservice.ws;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserHandshakeHandler extends DefaultHandshakeHandler {


    private final JwtDecoder jwtDecoder;

    public UserHandshakeHandler(JwtDecoder jwtDecoder) {
        super();
        this.jwtDecoder = jwtDecoder;

    }

    @Nullable
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            URI uri = request.getURI();
            String query = uri.getQuery(); // ex: token=eyJhbGciOi...

            if (query != null && query.startsWith("token=")) {
                String token = query.substring("token=".length());
                String userId = decodeJwtAndGetUsername(token);
                return new UsernamePasswordAuthenticationToken(userId, null, List.of());
            }


        }

        // Fallback si non authentifié
        return new Principal() {
            @Override
            public String getName() {
                return UUID.randomUUID().toString(); // ou null
            }
        };
    }

    private String decodeJwtAndGetUsername(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaimAsString("sub");
    }
}
