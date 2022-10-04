package br.com.abc.tdd.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    private String email;

    private String cpf;

    private String senha;
}
