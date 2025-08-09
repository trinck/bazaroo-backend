package org.mts.usersservice.web;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.mts.usersservice.configs.CountryContext;
import org.mts.usersservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
public class AuthController {



    @Autowired
    private IKeycloakService keycloakService;




    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UserRepresentation> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return this.keycloakService.getAllUsers(page, size, CountryContext.getRealm());
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public UserRepresentation updateUser(@RequestBody UserRepresentation   user){
        return this.keycloakService.updateUser(user, CountryContext.getRealm());
    }



    @GetMapping("/{id}")
    public UserRepresentation getUserById(@PathVariable String id){
        return this.keycloakService.getUserById(id ,CountryContext.getRealm());
    }


}
