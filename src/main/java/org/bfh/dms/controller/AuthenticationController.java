package org.bfh.dms.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @GetMapping(value = "/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated!");
    }

    @Setter
    @Getter
    private class AuthenticationBean {

        private String message;

        public AuthenticationBean(String message) {
            this.message = message;
        }
    }
}
