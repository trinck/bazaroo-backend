package org.mts.usersservice.services;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class KeycloakService implements IKeycloakService {

    private Keycloak  keycloak;

    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    /**
     * @param id
     * @param realm
     * @return
     */
    @Override
    public UserRepresentation getUserById(String id, String realm) {

        try {
            // Récupérer l'utilisateur par ID dans le realm spécifié
            return this.keycloak.realm(realm).users().get(id).toRepresentation();
        } catch (Exception e) {
            // Gérer les erreurs (par exemple, utilisateur non trouvé ou problème d'accès)
            throw new RuntimeException("Error in retrieving user by id: " + id + " in realm : " + realm, e);
        }
    }

    /**
     * @param email
     * @param realm
     * @return
     */
    @Override
    public UserRepresentation getUserByEmail(String email, String realm) {
        return this.keycloak.realm(realm).users().searchByEmail(email, true).get(0);
    }

    /**
     * @param username
     * @param realm
     * @return
     */
    @Override
    public UserRepresentation getUserByUsername(String username, String realm) {
        return this.keycloak.realm(realm).users().searchByUsername(username,true).get(0);
    }

    /**
     * @param username
     * @param realm
     * @return
     */
    @Override
    public UserRepresentation getUserByName(String username, String realm) {
        return this.keycloak.realm(realm).users().searchByLastName(username, true).get(0);
    }

    /**
     * @param realm
     * @return
     */
    @Override
    public List<UserRepresentation> getAllUsers(int page, int size,String realm) {
        return this.keycloak.realm(realm).users().list(page, size);
    }

    /**
     * @param user
     * @param realm
     * @return
     */
    @Override
    public UserRepresentation updateUser(UserRepresentation user, String realm) {
        try {
            // Récupérer l'utilisateur
            if (user == null) {
                throw new RuntimeException("User variable is null");
            }
            // Mettre à jour l'utilisateur dans Keycloak

            keycloak.realm(realm).users().get(user.getId()).update(user);

        } catch (jakarta.ws.rs.NotFoundException e) {
            throw new RuntimeException("Realm or user unknow", e);
        }

        return user;
    }
}
