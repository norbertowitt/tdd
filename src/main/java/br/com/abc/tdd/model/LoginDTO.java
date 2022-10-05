package br.com.abc.tdd.model;

import br.com.abc.tdd.enums.TipoLoginEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @ApiModelProperty(value = "CPF ou E-mail do usuário.", required = true, example = "rosangela.ferreira@edu.abc.br")
    private String usuario;

    @ApiModelProperty(value = "Senha em Base64", required = true, example = "MTIzNDU2Nzg=")
    private String senha;

    @ApiModelProperty(value = "Tipo do login: CPF ou EMAIL", required = true, example = "EMAIL")
    private TipoLoginEnum tipoLogin;

    public Boolean isThereAnyNullField() {
        try {
            for (Field f : this.getClass().getDeclaredFields()) {
                if (Objects.isNull(f.get(this)))
                    return true;
            }
        } catch (Exception e) {
            return true;
        }

        return false;
    }
}
