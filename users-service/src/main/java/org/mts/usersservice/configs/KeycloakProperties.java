package org.mts.usersservice.configs;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "keycloak.realms")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakProperties {

   private String json;
    private String defaultTenantId;
    private String defaultRealm;
    private List<RealmConfig> realms;

    public List<RealmConfig> getRealms() {
        if (realms == null) {
            realms = new ArrayList<>();
            if (!json.isEmpty()) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    realms = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, RealmConfig.class));
                } catch (Exception e) {
                    throw new RuntimeException("Error in parsing KEYCLOAK_REALMS_JSON", e);
                }
            }
            // Ajouter le realm par défaut si aucun realm n'est configuré
            if (realms.isEmpty() && defaultRealm != null) {
                RealmConfig defaultConfig = new RealmConfig();
                defaultConfig.setRealm(defaultRealm);
                defaultConfig.setTenantId(defaultTenantId);
                realms.add(defaultConfig);
            }
        }
        return realms;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RealmConfig {
        private String tenantId;
        private String realm;
    }

}
