package br.com.abc.tdd.controller;

import br.com.abc.tdd.model.LoginDTO;
import br.com.abc.tdd.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRest {

    private LoginService loginService;

    @Autowired
    public LoginRest(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/efetuar-login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> efetuarLogin(@RequestBody LoginDTO loginDTO) {
        loginService.efetuarLogin(loginDTO);
        return ResponseEntity.ok().build();
    }
}
