package org.mts.usersservice.services;

import org.mts.usersservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    public User creatUser(User user);
    public User getUserById(String id);
    public User updateUser(User user);
    public User deleteUserById(String id);
    public Page<User> getUsers(Pageable pageable);
    public List<User> getListUsers();
    public void updateProfile(String id, String profileUrl);
    public Page<User> findUsersByFirstname(String firstname, Pageable pageable);
}
