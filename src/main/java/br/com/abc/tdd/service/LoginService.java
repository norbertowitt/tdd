package br.com.abc.tdd.service;

import br.com.abc.tdd.exceptionhandler.exception.UserNotFoundException;
import br.com.abc.tdd.exceptionhandler.exception.UserValidationException;
import br.com.abc.tdd.model.LoginDTO;
import br.com.abc.tdd.repository.UsuarioRepository;
import br.com.caelum.stella.validation.CPFValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class LoginService {

    private static final String MSG_USUARIO_NAO_ENCONTRADO = "Nenhum usuário foi encontrado com as credenciais informadas.";
    private static final String MSG_DADOS_INVALIDOS = "Os dados informados são inválidos.";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private UsuarioRepository usuarioRepository;

    @Autowired
    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void efetuarLogin(LoginDTO loginDTO) {
        validarCredenciais(loginDTO);
    }
    
    private void validarCredenciais(LoginDTO loginDTO) {

        if ((Objects.isNull(loginDTO.getEmail()) && Objects.isNull(loginDTO.getCpf()))
                || Objects.isNull(loginDTO.getSenha())) {
            throw new UserValidationException(MSG_DADOS_INVALIDOS);
        }

        if (Objects.nonNull(loginDTO.getCpf())) {
            validarCpf(loginDTO.getCpf());
            validarCpfUsuarioESenha(loginDTO.getCpf(), loginDTO.getSenha());
        }

        if (Objects.nonNull(loginDTO.getEmail())) {
            validarEmail(loginDTO.getEmail());
            validarEmailUsuarioESenha(loginDTO.getEmail(), loginDTO.getSenha());
        }
    }

    private void validarCpf(String cpf) {
        try {
            new CPFValidator().assertValid(cpf);
        } catch (Exception e) {
            LOGGER.debug("O CPF informado é inválido.", e);
            throw new UserValidationException(MSG_DADOS_INVALIDOS, e);
        }
    }

    private void validarEmail(String email) {
        if (!email.endsWith("@edu.abc.br") || !Pattern.compile(EMAIL_REGEX_PATTERN).matcher(email).matches()) {
            LOGGER.debug("O e-mail {} não é válido.", email);
            throw new UserValidationException(MSG_DADOS_INVALIDOS);
        }
    }

    private void validarCpfUsuarioESenha(String cpf, String senha) {
        if (usuarioRepository.findAllByCpfAndSenha(cpf, senha).isEmpty()) {
            LOGGER.debug("Nenhum usuário foi encontrado com os parâmetros cpf e senha informados.");
            throw new UserNotFoundException(MSG_USUARIO_NAO_ENCONTRADO);
        }
    }

    private void validarEmailUsuarioESenha(String email, String senha) {
        if (usuarioRepository.findAllByEmailAndSenha(email, senha).isEmpty()) {
            LOGGER.debug("Nenhum usuário foi encontrado com os parâmetros e-mail e senha informados.");
            throw new UserNotFoundException(MSG_USUARIO_NAO_ENCONTRADO);
        }
    }
}
