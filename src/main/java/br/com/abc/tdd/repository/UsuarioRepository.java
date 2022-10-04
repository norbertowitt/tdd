package br.com.abc.tdd.repository;

import br.com.abc.tdd.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findAllByCpfAndSenha(String cpf, String senha);

    UsuarioEntity findAllByEmailAndSenha(String cpf, String senha);
}
