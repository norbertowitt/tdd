package br.com.abc.tdd.controller;

import br.com.abc.tdd.model.LoginDTO;
import br.com.abc.tdd.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/efetuar-login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> efetuarLogin(@RequestBody LoginDTO loginDTO) {

        log.info("ENTRADA /efetuar-login. Tipo do login: {}", loginDTO.getTipoLogin());

        loginService.efetuarLogin(loginDTO);

        log.info("SAÍDA /efetuar-login. Login do tipo {} foi autorizado.", loginDTO.getTipoLogin());

        return ResponseEntity.ok().build();
    }
}
