package com.efficiencynow.efficiencynow.Exceptions;

/**
 * Classe que contém exceções personalizadas para o sistema.
 */
public class Exceptions {

    /**
     * Exceção lançada quando a senha é inválida.
     */
    public static class InvalidPasswordException extends RuntimeException {
        /**
         * Construtor da exceção InvalidPasswordException.
         *
         * @param message Mensagem de erro.
         */
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    /**
     * Exceção lançada quando um e-mail duplicado é encontrado.
     */
    public static class DuplicateEmailException extends RuntimeException {
        /**
         * Construtor da exceção DuplicateEmailException.
         *
         * @param message Mensagem de erro.
         */
        public DuplicateEmailException(String message) {
            super(message);
        }
    }

    /**
     * Exceção lançada quando ocorre um erro de autenticação.
     */
    public static class AuthException extends RuntimeException {
        /**
         * Construtor da exceção AuthException.
         *
         * @param message Mensagem de erro.
         */
        public AuthException(String message) {
            super(message);
        }
    }
}
