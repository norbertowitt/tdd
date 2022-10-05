package br.com.abc.tdd.service;

import br.com.abc.tdd.exceptionhandler.exception.CaseNotImplementedException;
import br.com.abc.tdd.exceptionhandler.exception.UserNotFoundException;
import br.com.abc.tdd.exceptionhandler.exception.LoginDataValidationException;
import br.com.abc.tdd.model.LoginDTO;
import br.com.abc.tdd.repository.UsuarioRepository;
import br.com.caelum.stella.validation.CPFValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class LoginService {

    private static final String MSG_USUARIO_NAO_ENCONTRADO = "Nenhum usuário foi encontrado com as credenciais informadas.";
    private static final String MSG_DADOS_INVALIDOS = "Os dados informados são inválidos.";
    private static final String MSG_TIPO_LOGIN_NAO_IMPLEMENTADO = "Login via %s ainda não implementado.";

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

        if (loginDTO.isThereAnyNullField()) {
            log.error("Algum parâmetro essencial está nulo.");
            throw new LoginDataValidationException(MSG_DADOS_INVALIDOS);
        }

        validarSenhaBase64(loginDTO.getSenha());

        switch (loginDTO.getTipoLogin()) {
            case CPF -> {
                validarCpf(loginDTO.getUsuario());
                validarCpfUsuarioESenha(loginDTO.getUsuario(), loginDTO.getSenha());
            }
            case EMAIL -> {
                validarEmail(loginDTO.getUsuario());
                validarEmailUsuarioESenha(loginDTO.getUsuario(), loginDTO.getSenha());
            }
            default -> {
                String message = String.format(MSG_TIPO_LOGIN_NAO_IMPLEMENTADO, loginDTO.getTipoLogin());
                log.error(message);
                throw new CaseNotImplementedException(message);
            }
        }
    }

    private void validarSenhaBase64(String senha) {
        try {
            Base64.decodeBase64(senha);
            log.info("A senha informada está codificada em Base64.");
        } catch (Exception e) {
            log.error("A senha informada não está codificada em Base64.");
            throw new LoginDataValidationException(MSG_DADOS_INVALIDOS);
        }
    }

    private void validarCpf(String cpf) {
        try {
            new CPFValidator().assertValid(cpf);
            log.info("O CPF {} é valido.", cpf);
        } catch (Exception e) {
            log.error("O CPF {} é inválido. {}", cpf, e.getMessage());
            throw new LoginDataValidationException(MSG_DADOS_INVALIDOS, e);
        }
    }

    private void validarEmail(String email) {
        if (!email.endsWith("@edu.abc.br") || !Pattern.compile(EMAIL_REGEX_PATTERN).matcher(email).matches()) {
            log.error("O e-mail {} não é válido.", email);
            throw new LoginDataValidationException(MSG_DADOS_INVALIDOS);
        }
        log.info("O e-mail {} informado é válido.", email);
    }

    private void validarCpfUsuarioESenha(String cpf, String senha) {
        if (usuarioRepository.findAllByCpfAndSenha(cpf, senha).isEmpty()) {
            log.error("Nenhum usuário foi encontrado com os parâmetros cpf e senha informados.");
            throw new UserNotFoundException(MSG_USUARIO_NAO_ENCONTRADO);
        }
        log.info("Foi encontrado um usuário com o CPF e senha informados.");
    }

    private void validarEmailUsuarioESenha(String email, String senha) {
        if (usuarioRepository.findAllByEmailAndSenha(email, senha).isEmpty()) {
            log.error("Nenhum usuário foi encontrado com os parâmetros e-mail e senha informados.");
            throw new UserNotFoundException(MSG_USUARIO_NAO_ENCONTRADO);
        }
        log.info("Foi encontrado um usuário com o e-mail e senha informados.");
    }
}
