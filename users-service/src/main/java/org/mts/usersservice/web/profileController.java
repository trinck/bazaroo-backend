package org.mts.usersservice.web;


import org.mts.usersservice.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profiles")
public class profileController {
    @Autowired
    private IUserService userService;



}
