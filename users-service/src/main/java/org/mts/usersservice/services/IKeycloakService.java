package org.mts.usersservice.services;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloakService {
    public UserRepresentation getUserById(String id, String realm);
    public UserRepresentation getUserByEmail(String email, String realm);
    public UserRepresentation getUserByUsername(String username, String realm);
    public UserRepresentation getUserByName(String username, String realm);
    public List<UserRepresentation> getAllUsers(int page, int size,String realm);
    public UserRepresentation updateUser(UserRepresentation user, String realm);
}
