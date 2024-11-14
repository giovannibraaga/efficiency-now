package com.efficiencynow.efficiencynow.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa um nó de usuário.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNode {
    /**
     * Email do usuário.
     */
    private String email;

    /**
     * Hash da senha do usuário.
     */
    private String passwordHash;

    /**
     * Nome do usuário.
     */
    private String name;

    /**
     * ID do usuário.
     */
    private Long id;

    /**
     * Token do usuário.
     */
    private String token;

    /**
     * Construtor para criar um novo nó de usuário.
     *
     * @param email        O email do usuário.
     * @param name         O nome do usuário.
     * @param id           O ID do usuário.
     */
    public UserNode(String email, String name, Long id) {
        this.email = email;
        this.name = name;
        this.id = id;
    }
}