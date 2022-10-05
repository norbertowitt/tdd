package br.com.abc.tdd.repository;

import br.com.abc.tdd.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findAllByCpfAndSenha(String cpf, String senha);

    Optional<UsuarioEntity> findAllByEmailAndSenha(String cpf, String senha);
}
