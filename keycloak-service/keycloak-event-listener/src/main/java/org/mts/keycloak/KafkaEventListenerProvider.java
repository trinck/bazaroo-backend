package org.mts.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.*;
import org.keycloak.utils.KeycloakSessionUtil;

import java.util.stream.Stream;


public class KafkaEventListenerProvider implements EventListenerProvider {
    private static final String TOPIC_ADMIN = "keycloak-admin-events";
    private final KafkaProducerConfig kafkaProducer;
    private static final String TOPIC_USER = "keycloak-user-events";

    public KafkaEventListenerProvider(KafkaProducerConfig kafkaProducer) {
        this.kafkaProducer = kafkaProducer;

    }


    /**
     * @param event
     */
    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.REGISTER || event.getType() == EventType.UPDATE_PROFILE || event.getType() == EventType.DELETE_ACCOUNT) {
            try {
                KeycloakSession keycloakSession = KeycloakSessionUtil.getKeycloakSession();
                UserProvider userProvider = keycloakSession.users();
                // Récupérer l'utilisateur depuis la session Keycloak
                RealmModel realm = keycloakSession.realms().getRealm(event.getRealmId());
                UserModel user = userProvider.getUserById(realm, event.getUserId());


                if (user != null) {
                    // Construire un objet JSON avec les détails de l'utilisateur
                    ObjectMapper objectMapper = new ObjectMapper();
                    ObjectNode eventNode = objectMapper.createObjectNode();
                    eventNode.put("type", event.getType().name());
                    eventNode.put("userId", event.getUserId());
                   this.addUserAttributes(eventNode, user);
                    eventNode.put("ipAddress", event.getIpAddress());
                    eventNode.put("clientId", event.getClientId());


                    // Récupérer UNIQUEMENT les rôles par défaut attribués à l'inscription
                    ArrayNode rolesArray = objectMapper.createArrayNode();
                    // ClientModel clientModel = keycloakSession.getContext().getClient();

                    // clientModel.rol

                    //Stream<RoleModel> roles = user.getRoleMappingsStream();
                    Stream<RoleModel> roles = realm.getDefaultRole().getCompositesStream();
                    roles.forEach(r -> rolesArray.add(r.getName()));


                    eventNode.set("roles", rolesArray);
                    String topic = System.getenv("APP_BROKER_TOPICS_USER");
                    if (topic == null || topic.isEmpty()){
                        topic = TOPIC_USER;
                    }

                    // Envoyer l'événement Kafka
                    this.kafkaProducer.sendMessage(topic, eventNode.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * @param adminEvent
     * @param b
     */
    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        OperationType type = adminEvent.getOperationType();
        if ((type == OperationType.CREATE || type == OperationType.UPDATE || type == OperationType.DELETE) && adminEvent.getResourceType() == ResourceType.USER) {

            try {
                KeycloakSession keycloakSession = KeycloakSessionUtil.getKeycloakSession();
                UserProvider userProvider = keycloakSession.users();
                RealmModel realm = keycloakSession.realms().getRealm(adminEvent.getRealmId());
                //UserModel user = userProvider.getUserById(realm, adminEvent.getResourcePath());

                // Récupérer l'ID utilisateur à partir du chemin de ressource
                String resourcePath = adminEvent.getResourcePath();
                if (resourcePath != null && resourcePath.startsWith("users/")) {
                    String userId = resourcePath.substring(6); // Supprime "users/" du début

                    // Récupérer l'utilisateur depuis Keycloak
                    UserModel user = userProvider.getUserById(realm, userId);

                    if (user != null) {
                        // Construire un objet JSON avec les détails de l'utilisateur
                        ObjectMapper objectMapper = new ObjectMapper();
                        ObjectNode eventNode = objectMapper.createObjectNode();
                        eventNode.put("type", adminEvent.getOperationType().name());
                        eventNode.put("userId", user.getId());
                       this.addUserAttributes(eventNode,user);
                        eventNode.put("ipAddress", adminEvent.getAuthDetails().getIpAddress());
                        eventNode.put("clientId", adminEvent.getAuthDetails().getClientId());


                        // Récupérer UNIQUEMENT les rôles par défaut attribués à l'inscription
                        ArrayNode rolesArray = objectMapper.createArrayNode();
                        // ClientModel clientModel = keycloakSession.getContext().getClient();


                        //Stream<RoleModel> roles = user.getRoleMappingsStream();
                        Stream<RoleModel> roles = realm.getDefaultRole().getCompositesStream();
                        roles.forEach(r -> rolesArray.add(r.getName()));


                        eventNode.set("roles", rolesArray);
                        String topic = System.getenv("APP_BROKER_TOPICS_USER");
                        if (topic == null || topic.isEmpty()){
                            topic = TOPIC_ADMIN;
                        }
                        //String message = "Admin event: " + adminEvent.getOperationType() + " - Resource: " + adminEvent.getResourceType();
                        this.kafkaProducer.sendMessage(topic, eventNode.toString());
                    }


                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }

    private void addUserAttributes(ObjectNode node, UserModel userModel){

        node.put("username", userModel.getUsername());
        node.put("email", userModel.getEmail());
        node.put("firstName", userModel.getFirstName());
        node.put("lastName", userModel.getLastName());
        node.put("emailVerified", userModel.isEmailVerified());
        node.put("enabled", userModel.isEnabled());
        node.put("createdTimesTamp", userModel.getCreatedTimestamp());
    }

    /**
     *
     **/
    @Override
    public void close() {
        this.kafkaProducer.close();
    }
}
