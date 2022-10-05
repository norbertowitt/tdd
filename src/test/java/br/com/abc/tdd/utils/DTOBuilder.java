package br.com.abc.tdd.utils;

import br.com.abc.tdd.enums.TipoLoginEnum;
import br.com.abc.tdd.entity.UsuarioEntity;
import br.com.abc.tdd.model.LoginDTO;

import java.util.Base64;
import java.util.Optional;

public class DTOBuilder {

    private static final String NOME_USUARIO = "João Pedro";
    private static final String EMAIL_VALIDO = "joao.pedro@edu.abc.br";
    private static final String TELEFONE_VALIDO = "+5591982152453";
    private static final String CPF_VALIDO = "69484909728";
    private static final String SENHA = Base64.getEncoder().encodeToString("123456".getBytes());

    private static final String EMAIL_INVALIDO = "joao.pedro.@edu.abc.br";
    private static final String EMAIL_DOMINIO_INVALIDO = "joao.pedro@gmail.com";
    private static final String CPF_INVALIDO = "12345678901";

    public static LoginDTO buildLoginDTOComCPFValido() {
        return LoginDTO.builder()
                .usuario(CPF_VALIDO)
                .senha(SENHA)
                .tipoLogin(TipoLoginEnum.CPF)
                .build();
    }

    public static LoginDTO buildLoginDTOComEmailValido() {
        return LoginDTO.builder()
                .usuario(EMAIL_VALIDO)
                .senha(SENHA)
                .tipoLogin(TipoLoginEnum.EMAIL)
                .build();
    }

    public static LoginDTO buildLoginDTOComTelefoneValido() {
        return LoginDTO.builder()
                .usuario(TELEFONE_VALIDO)
                .senha(SENHA)
                .tipoLogin(TipoLoginEnum.TELEFONE)
                .build();
    }

    public static LoginDTO buildLoginDTOComCPFInvalido() {
        return LoginDTO.builder()
                .usuario(CPF_INVALIDO)
                .senha(SENHA)
                .tipoLogin(TipoLoginEnum.CPF)
                .build();
    }

    public static LoginDTO buildLoginDTOComEmailInvalido() {
        return LoginDTO.builder()
                .usuario(EMAIL_INVALIDO)
                .senha(SENHA)
                .tipoLogin(TipoLoginEnum.EMAIL)
                .build();
    }

    public static LoginDTO buildLoginDTOComDominioEmailInvalido() {
        return LoginDTO.builder()
                .usuario(EMAIL_DOMINIO_INVALIDO)
                .senha(SENHA)
                .tipoLogin(TipoLoginEnum.EMAIL)
                .build();
    }

    public static Optional<UsuarioEntity> buildOptionalUsuarioEntity() {
        return Optional.of(UsuarioEntity.builder()
                        .id(1L)
                        .nome(NOME_USUARIO)
                        .email(EMAIL_VALIDO)
                        .cpf(CPF_VALIDO)
                        .senha(SENHA)
                .build());
    }

    public static Optional<UsuarioEntity> buildEmptyOptionalUsuarioEntity() {
        return Optional.empty();
    }

}
