package com.efficiencynow.efficiencynow.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de Transferência de Dados para Usuário.
 * Esta classe é usada para transferir dados do usuário entre processos.
 */
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    /**
     * O ID do usuário.
     */
    private Long id;

    /**
     * O nome do usuário.
     * Não deve estar em branco e deve ter pelo menos 3 caracteres.
     */
    @NotBlank(message = "É obrigatório informar o seu nome")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String name;

    /**
     * O e-mail do usuário.
     * Não deve estar em branco e deve estar em um formato de e-mail válido.
     */
    @NotBlank(message = "É obrigatório informar o seu e-mail")
    @Email
    private String email;

    /**
     * A senha do usuário.
     * Não deve estar em branco e deve ter pelo menos 6 caracteres.
     */
    @NotBlank(message = "É obrigatório informar a sua senha")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
}
