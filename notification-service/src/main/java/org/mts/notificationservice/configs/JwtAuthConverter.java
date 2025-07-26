package org.mts.notificationservice.configs;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    /**
     * @param source
     * @return
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(source).stream(),
                extractResourceRoles(source).stream()
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(source, authorities, source.getClaimAsString("sub"));
    }

    private Collection<GrantedAuthority> extractResourceRoles(Jwt source) {

        Map<String, Object> realmAccess;
        Collection<String> roles;
        if (source.getClaim("realm_access")==null) {

            return List.of();
        }
        realmAccess = source.getClaim("realm_access");
        roles = (Collection<String>) realmAccess.get("roles");
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

    }


}
