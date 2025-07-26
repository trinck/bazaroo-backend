package org.mts.announcesservice.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicRealmJwtDecoder implements JwtDecoder {

    // Liste des realms autorisés (à adapter)
    private static final Set<String> ALLOWED_REALMS = Set.of("realm-fr", "mts-adryvo", "realm-ci");
    private final String baseIssuerUri;
    private final String baseJwkSetUri;
    // Cache pour réutiliser les decoders par JWK URI
    private final Map<String, JwtDecoder> cache = new ConcurrentHashMap<>();

    public DynamicRealmJwtDecoder(
            @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String baseIssuerUri,
            @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String baseJwkSetUri
    ) {
        this.baseIssuerUri = baseIssuerUri;
        this.baseJwkSetUri = baseJwkSetUri;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        // Extraire le payload sans vérification (non signé)
        String payload = token.split("\\.")[1];
        String json = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
        try {
            Map<String, Object> jsonObject = (Map<String, Object>) new ObjectMapper().readValue(json, Map.class);
            // Extraire le realm depuis le champ `iss`

            String issuer = (String) jsonObject.get("iss");
            String realm = issuer.replaceAll(".*/realms/", "");

            if (!ALLOWED_REALMS.contains(realm)) {
                throw new JwtException("Realm not allowed: " + realm);
            }

            // Construire les URI spécifiques au realm
            String issuerUri = baseIssuerUri.replaceAll("realms/[^/]+", "realms/" + realm);
            String jwkSetUri = baseJwkSetUri.replaceAll("realms/[^/]+", "realms/" + realm);

            // Récupérer ou construire un JwtDecoder spécifique
            return cache.computeIfAbsent(jwkSetUri, uri -> {
                NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(uri).build();

                // Valider que le iss du token correspond bien à l'issuer attendu
                OAuth2TokenValidator<Jwt> validator = JwtValidators.createDefaultWithIssuer(issuerUri);
                decoder.setJwtValidator(validator);

                return decoder;
            }).decode(token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
