package org.mts.usersservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("users-management")
public class UsersManagement {

    @GetMapping("/users")
    public Map<String,String> getUsers(){

        Map<String,String> users = new HashMap<>();

        for(int i = (int) (Math.random() * 10); i>0; i--){

            users.put("key_"+i, "value:"+i*10);
        }

        return users;
    }

    @GetMapping("/profil")
    public Map<String,String> getProfil(){

        Map<String,String> users = new HashMap<>();

            users.put("Name", "MOULOUNGUI IBIATSI");
        users.put("Firstname", "MGontran");
        users.put("Birthday", "26/04/1996");


        return users;
    }
}
