package br.com.abc.tdd.service;

import br.com.abc.tdd.exceptionhandler.exception.UserNotFoundException;
import br.com.abc.tdd.exceptionhandler.exception.UserValidationException;
import br.com.abc.tdd.model.LoginDTO;
import br.com.abc.tdd.repository.UsuarioRepository;
import br.com.abc.tdd.utils.DTOBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveEfetuarLoginComCpfComSucesso() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();

        Mockito.doReturn(DTOBuilder.buildOptionalUsuarioEntity())
                        .when(usuarioRepository).findAllByCpfAndSenha(loginDTO.getCpf(), loginDTO.getSenha());
        
        Assertions.assertThatCode(() -> loginService.efetuarLogin(loginDTO))
                .doesNotThrowAnyException();
    }

    @Test
    public void deveEfetuarLoginComEmailComSucesso() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailValido();

        Mockito.doReturn(DTOBuilder.buildOptionalUsuarioEntity())
                .when(usuarioRepository).findAllByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha());

        Assertions.assertThatCode(() -> loginService.efetuarLogin(loginDTO))
                .doesNotThrowAnyException();

        Mockito.verify(usuarioRepository).findAllByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha());
    }

    @Test
    public void deveLancarExcecaoAoInformarDadosNulos() {
        LoginDTO loginDTO = new LoginDTO();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    @Test
    public void deveLancarExcecaoAoInformarCpfNaoNuloESenhaNula() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();
        loginDTO.setSenha(null);

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    @Test
    public void deveLancarExcecaoAoInformarEmailNaoNuloESenhaNula() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailValido();
        loginDTO.setSenha(null);

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    @Test
    public void deveLancarExcecaoAoInformarCpfInvalido() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFInvalido();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    @Test
    public void deveLancarExcecaoAoInformarEmailInvalido() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailInvalido();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    @Test
    public void deveLancarExcecaoAoInformarEmailComDominioInvalido() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComDominioEmailInvalido();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarUsuarioNaBaseComCpfESenha() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();

        Mockito.doReturn(DTOBuilder.buildEmptyOptionalUsuarioEntity())
                .when(usuarioRepository).findAllByCpfAndSenha(loginDTO.getCpf(), loginDTO.getSenha());

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Nenhum usuário foi encontrado com as credenciais informadas.");
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarUsuarioNaBaseComEmailESenha() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailValido();

        Mockito.doReturn(DTOBuilder.buildEmptyOptionalUsuarioEntity())
                .when(usuarioRepository).findAllByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha());

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Nenhum usuário foi encontrado com as credenciais informadas.");
    }
}
