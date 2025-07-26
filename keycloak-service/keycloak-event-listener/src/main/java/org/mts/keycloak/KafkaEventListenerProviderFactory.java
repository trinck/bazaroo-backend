package org.mts.keycloak;

import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class KafkaEventListenerProviderFactory implements EventListenerProviderFactory {


    /**
     * @param keycloakSession
     * @return
     */
    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new KafkaEventListenerProvider(new KafkaProducerConfig());
    }

    /**
     * @param scope
     */
    @Override
    public void init(org.keycloak.Config.Scope scope) {


    }

    /**
     * @param keycloakSessionFactory
     */
    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    /**
     *
     */
    @Override
    public void close() {

    }

    /**
     * @return
     */
    @Override
    public String getId() {
        return "kafka-event-listener";
    }
}
