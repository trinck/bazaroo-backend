package org.mts.usersservice.services;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.User;
import org.mts.usersservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    /**
     * @param user
     * @return
     */
    @Override
    public User creatUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow();
    }

    /**
     * @param user
     * @return
     */
    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public User deleteUserById(String id) {
       User user = this.userRepository.findById(id).orElseThrow();
       this.userRepository.deleteById(id);
        return user;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<User> getUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<User> getListUsers() {
        return this.userRepository.findAll();
    }

    /**
     * @param id
     * @param profileUrl
     */
    @Override
    public void updateProfile(String id, String profileUrl) {

        User user = this.getUserById(id);
        user.setProfileUrl(profileUrl);
        this.userRepository.save(user);
    }

    /**
     * @param firstname
     * @param pageable
     * @return
     */
    @Override
    public Page<User> findUsersByFirstname(String firstname, Pageable pageable) {
        return this.userRepository.findByFirstnameContainsIgnoreCase(firstname, pageable);
    }
}
