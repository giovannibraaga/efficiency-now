package com.efficiencynow.efficiencynow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade de Usuário.
 * Esta classe representa a entidade de usuário no banco de dados.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    /**
     * O ID único do usuário.
     * Gerado automaticamente pela estratégia de identidade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O nome do usuário.
     * Não pode ser nulo.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * O e-mail do usuário.
     * Deve ser único e não nulo.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * A senha do usuário.
     * Não pode ser nula.
     */
    @Column(name = "password", nullable = false)
    private String password;
}
