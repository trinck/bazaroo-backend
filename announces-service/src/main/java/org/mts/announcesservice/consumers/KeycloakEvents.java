package org.mts.announcesservice.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mts.announcesservice.entities.Preference;
import org.mts.announcesservice.enums.KeycloakAdminEvent;
import org.mts.announcesservice.enums.KeycloakEvent;
import org.mts.announcesservice.repositories.PreferenceRepository;
import org.mts.announcesservice.service.IAnnounceSearchService;
import org.mts.announcesservice.service.IPreferenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

@Component
@Slf4j
public class KeycloakEvents {

    private final IPreferenceService preferenceService;
    private final IAnnounceSearchService searchService;

    public KeycloakEvents( PreferenceRepository preferenceRepository, IPreferenceService preferenceService, IAnnounceSearchService searchService) {
        this.preferenceService = preferenceService;
        this.searchService = searchService;
    }

    @Bean
    public Consumer<String> userEvents() {
        return (this::deserializeUser);
    }

    @Bean
    public Consumer<String> adminEvents() {
        return (this::deserializeUser);
    }


    private void deserializeUser(String json) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Object o = objectMapper.readValue(json, Object.class);
            JsonNode jsonNodes = objectMapper.convertValue(o, JsonNode.class);
            List<String> roles = objectMapper.convertValue(jsonNodes.get("roles"), List.class);

            if (jsonNodes.get("type").asText().equalsIgnoreCase(KeycloakAdminEvent.CREATE.name()) || jsonNodes.get("type").asText().equalsIgnoreCase(KeycloakEvent.REGISTER.name())) {
                createPreference(jsonNodes, roles);
            } else if (jsonNodes.get("type").asText().equalsIgnoreCase(KeycloakAdminEvent.DELETE.name()) || jsonNodes.get("type").asText().equalsIgnoreCase(KeycloakEvent.DELETE_ACCOUNT.name())) {
                this.preferenceService.deleteByUserId(jsonNodes.get("userId").asText());
                this.searchService.deleteAllSavedSearchByUserId(jsonNodes.get("userId").asText());
            } else {
                try {
                    Preference preference = this.preferenceService.getPreferenceByUserId(jsonNodes.get("userId").asText());
                    preference.setRoles(roles.stream().reduce((s, s2) -> s.concat(" " + s2)).get());
                    preference.setEnabled(jsonNodes.get("enabled").asBoolean());
                    this.preferenceService.save(preference);

                }catch (NoSuchElementException ignored){
                    Preference preference = createPreference(jsonNodes, roles);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Preference createPreference(JsonNode jsonNodes, List<String> roles) {
        Preference preference = new Preference();
        preference.setUserId(jsonNodes.get("userId").asText());
        preference.setRoles(roles.stream().reduce((s, s2) -> s.concat(" " + s2)).get());
        preference.setBeNotified(true);
        preference.setEnabled(jsonNodes.get("enabled").asBoolean());

        return this.preferenceService.save(preference);
    }
}
