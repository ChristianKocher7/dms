package org.bfh.dms.infrastructure.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class UserInfoController {

    @GetMapping(value = "/user")
    public Principal user(Principal user) {
        System.out.println("getting logged in user" + user.getName());
        return user;
    }

}
