package br.com.abc.tdd.service;

import br.com.abc.tdd.model.LoginDTO;
import br.com.abc.tdd.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Base64;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginServiceTest {

    private static final String EMAIL = "joao.pedro@edu.abc.br";
    private static final String CPF = "69484909728";
    private static final String SENHA = Base64.getEncoder().encodeToString("123456".getBytes());

    @InjectMocks
    @Spy
    private LoginService loginService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveEfetuarLoginComSucesso() {
        LoginDTO loginDTO = buildLoginDTO();

        Assertions.assertThatCode(() -> loginService.efetuarLogin(loginDTO))
                .doesNotThrowAnyException();
    }

    private LoginDTO buildLoginDTO() {
        return LoginDTO.builder()
                .cpf(CPF)
                .email(EMAIL)
                .senha(SENHA)
                .build();
    }
}
