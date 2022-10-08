package br.com.abc.tdd.service;

import br.com.abc.tdd.exceptionhandler.exception.CaseNotImplementedException;
import br.com.abc.tdd.exceptionhandler.exception.UserNotFoundException;
import br.com.abc.tdd.exceptionhandler.exception.LoginDataValidationException;
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

    // Cenário de teste 1
    @Test
    public void deveEfetuarLoginPorCpfComSucesso() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();

        Mockito.doReturn(DTOBuilder.buildOptionalUsuarioEntity())
                .when(usuarioRepository).findAllByCpfAndSenha(loginDTO.getUsuario(), loginDTO.getSenha());
        
        Assertions.assertThatCode(() -> loginService.efetuarLogin(loginDTO))
                .doesNotThrowAnyException();

        Mockito.verify(usuarioRepository)
                .findAllByCpfAndSenha(loginDTO.getUsuario(), loginDTO.getSenha());
    }

    // Cenário de teste 2
    @Test
    public void deveEfetuarLoginPorEmailComSucesso() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailValido();

        Mockito.doReturn(DTOBuilder.buildOptionalUsuarioEntity())
                .when(usuarioRepository).findAllByEmailAndSenha(loginDTO.getUsuario(), loginDTO.getSenha());

        Assertions.assertThatCode(() -> loginService.efetuarLogin(loginDTO))
                .doesNotThrowAnyException();

        Mockito.verify(usuarioRepository)
                .findAllByEmailAndSenha(loginDTO.getUsuario(), loginDTO.getSenha());
    }

    // Cenário de teste 3
    @Test
    public void deveLancarExcecaoAoEfetuarLoginComDadosNulos() {
        LoginDTO loginDTO = new LoginDTO();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    // Cenário de teste 4
    @Test
    public void deveLancarExcecaoAoInformarUsuarioNulo() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();
        loginDTO.setUsuario(null);

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    // Cenário de teste 5
    @Test
    public void deveLancarExcecaoAoInformarSenhaNula() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();
        loginDTO.setSenha(null);

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    // Cenário de teste 6
    @Test
    public void deveLancarExcecaoAoInformarTipoLoginNulo() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();
        loginDTO.setTipoLogin(null);

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }


    // Cenário de teste 7
    @Test
    public void deveLancarExcecaoAoInformarSenhaNaoCodificadaEmBase64() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailValido();
        loginDTO.setSenha("123456");

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    // Cenário de teste 8
    @Test
    public void deveLancarExcecaoAoInformarCpfInvalido() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFInvalido();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    // Cenário de teste 9
    @Test
    public void deveLancarExcecaoAoInformarEmailInvalido() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailInvalido();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    // Cenário de teste 10
    @Test
    public void deveLancarExcecaoAoInformarEmailComDominioInvalido() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComDominioEmailInvalido();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(LoginDataValidationException.class)
                .hasMessage("Os dados informados são inválidos.");
    }

    // Cenário de teste 11
    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarUsuarioNaBaseComCpfESenha() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComCPFValido();

        Mockito.doReturn(DTOBuilder.buildEmptyOptionalUsuarioEntity())
                .when(usuarioRepository).findAllByCpfAndSenha(loginDTO.getUsuario(), loginDTO.getSenha());

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Nenhum usuário foi encontrado com as credenciais informadas.");
    }

    // Cenário de teste 12
    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarUsuarioNaBaseComEmailESenha() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComEmailValido();

        Mockito.doReturn(DTOBuilder.buildEmptyOptionalUsuarioEntity())
                .when(usuarioRepository).findAllByEmailAndSenha(loginDTO.getUsuario(), loginDTO.getSenha());

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Nenhum usuário foi encontrado com as credenciais informadas.");
    }

    // Cenário de teste 13
    @Test
    public void deveLancarExcecaoQuandoEfetuarLoginComTipoNaoImplementado() {
        LoginDTO loginDTO = DTOBuilder.buildLoginDTOComTelefoneValido();

        Assertions.assertThatThrownBy(() -> loginService.efetuarLogin(loginDTO))
                .isInstanceOf(CaseNotImplementedException.class)
                .hasMessage(String.format("Login via %s ainda não implementado.", loginDTO.getTipoLogin()));
    }
}
