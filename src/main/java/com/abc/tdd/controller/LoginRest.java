package com.abc.tdd.controller;

import com.abc.tdd.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRest {

    private LoginService loginService;

    @Autowired
    public LoginRest(LoginService loginService) {
        this.loginService = loginService;
    }
}
