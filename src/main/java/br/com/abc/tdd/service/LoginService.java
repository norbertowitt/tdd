package br.com.abc.tdd.service;

import br.com.abc.tdd.model.LoginDTO;
import br.com.abc.tdd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class LoginService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void efetuarLogin(LoginDTO loginDTO) {
        validarCredenciais(loginDTO);
    }
    
    private void validarCredenciais(LoginDTO loginDTO) {

        if (Objects.isNull(loginDTO.getEmail()) && Objects.isNull(loginDTO.getCpf())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "E-mail e CPF são nulos.");
        }

        if (Objects.nonNull(loginDTO.getCpf())) {
            validarCpf(loginDTO.getCpf());
        } else if (Objects.nonNull(loginDTO.getEmail())) {
            validarEmail(loginDTO.getEmail());
        }

        validarSenha(loginDTO.getSenha());
    }

    private void validarCpf(String cpf) {

    }

    private void validarEmail(String email) {

    }

    private void validarSenha(String senha) {
        if (Objects.nonNull(senha)) {

        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A senha informada é nula.");
        }
    }
}
