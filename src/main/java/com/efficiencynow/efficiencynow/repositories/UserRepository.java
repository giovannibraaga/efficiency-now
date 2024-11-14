package com.efficiencynow.efficiencynow.repositories;

import com.efficiencynow.efficiencynow.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repositório de Usuário.
 * Esta interface é usada para realizar operações de banco de dados na entidade UserEntity.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Busca um usuário pelo email.
     *
     * @param email O email do usuário a ser buscado.
     * @return O usuário encontrado, se existir.
     */
    @Query("select ue from UserEntity ue where ue.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    /**
     * Exclui um usuário pelo email.
     *
     * @param email O email do usuário a ser excluído.
     */
    void deleteByEmail(String email);
}
