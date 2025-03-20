package org.mts.usersservice.services;

import org.mts.usersservice.entities.Auth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuthService {


    public Auth creatAuth(Auth auth);

    public Auth getAuthById(String id);

    public Auth updateAuth(Auth auth);

    public Auth deleteAuthById(String id);

    public Page<Auth> getAuths(Pageable pageable);

    public List<Auth> getListAuths();

    public Auth loadAuthByUsername(String username);

    public Auth loadAuthByUsernameOrEmailOrTel(String identifier);
}
