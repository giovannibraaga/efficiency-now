package com.efficiencynow.efficiencynow.utils;

import com.efficiencynow.efficiencynow.Exceptions.Exceptions.InvalidPasswordException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe utilitária para codificação e verificação de senhas.
 */
public class PasswordEncoder {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Faz o hash na senha fornecida se ela atender aos critérios de validação.
     *
     * @param password A senha a ser hasheada.
     * @return A senha hasheada.
     * @throws IllegalArgumentException Se a senha não atender aos critérios de validação.
     */
    public static String encodePassword(String password) {
        if (!isValidPassword(password)) {
            throw new InvalidPasswordException("A senha deve conter pelo menos 6 caracteres, uma letra maiúscula, um número e um caractere especial.");
        }
        return encoder.encode(password);
    }

    /**
     * Verifica se a senha fornecida corresponde à senha hasheada.
     *
     * @param password        A senha em texto simples.
     * @param encodedPassword A senha hasheada.
     * @return true se as senhas corresponderem, false caso contrário.
     */
    public static boolean checkPassword(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }

    /**
     * Verifica se a senha atende aos critérios de validação.
     *
     * @param password A senha a ser validada.
     * @return true se a senha atender aos critérios, false caso contrário.
     */
    private static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$");
    }
}