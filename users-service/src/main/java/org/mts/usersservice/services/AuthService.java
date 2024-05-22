package org.mts.usersservice.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.Auth;
import org.mts.usersservice.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthService implements IAuthService{

    @Autowired
    private AuthRepository authRepository;

    /**
     * @param auth
     * @return
     */
    @Override
    public Auth creatAuth(Auth auth) {
        return this.authRepository.save(auth);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Auth getAuthById(Long id) {
        return null;
    }

    /**
     * @param auth
     * @return
     */
    @Override
    public Auth updateAuth(Auth auth) {
        return this.authRepository.save(auth);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Auth deleteAuthById(Long id) {

       Auth auth = this.authRepository.findById(id).orElseThrow();
       this.authRepository.deleteById(id);
        return auth;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Auth> getAuths(Pageable pageable) {
        return this.authRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Auth> getListAuths() {
        return this.authRepository.findAll();
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Auth loadAuthByUsername(String username) {
        return this.authRepository.findByUsername(username).orElseThrow();
    }

    /**
     * @param email
     * @return
     */
    @Override
    public Auth loadAuthByEmail(String email) {
        return this.authRepository.findByEmail(email).orElseThrow();
    }
}
