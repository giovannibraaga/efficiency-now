package com.efficiencynow.efficiencynow.controllers;

import com.efficiencynow.efficiencynow.Exceptions.Exceptions.AuthException;
import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para gerenciar usuários.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registra um novo usuário.
     *
     * @param userDTO Os dados do usuário a ser registrado.
     * @return A resposta HTTP com o usuário registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.status(201).body(registeredUser);
    }

    /**
     * Obtém um usuário pelo email.
     *
     * @param email O email do usuário a ser buscado.
     * @return A resposta HTTP com o usuário encontrado ou 404 se não encontrado.
     */
    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza o login de um usuário.
     *
     * @param userDTO Os dados do usuário para login.
     * @return A resposta HTTP com o usuário logado e o cookie de sessão, ou 401 se a autenticação falhar.
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            UserDTO user = userService.login(userDTO.getEmail(), userDTO.getPassword());

            ResponseCookie sessionCookie = ResponseCookie.from("SESSION", user.getToken())
                    .httpOnly(true)
                    .maxAge(7 * 24 * 60 * 60)
                    .secure(false)
                    .path("/")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                    .body(user);
        } catch (AuthException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Obtém o perfil do usuário autenticado.
     *
     * @param sessionToken O token de sessão do cookie.
     * @return A resposta HTTP com o perfil do usuário ou 401 se não autenticado, ou 404 se o usuário não for encontrado.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@CookieValue(name = "SESSION", required = false) String sessionToken) {
        if (sessionToken == null || !userService.isAuthenticated(sessionToken)) {
            return ResponseEntity.status(401).build();
        }

        String email = userService.getEmailFromSession(sessionToken);
        Optional<UserDTO> userOptional = userService.findByEmail(email);

        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    /**
     * Realiza o logout do usuário.
     *
     * @param sessionToken O token de sessão do cookie.
     * @return A resposta HTTP com o cookie de sessão deletado ou 400 se o token não for fornecido, ou 401 se a autenticação falhar.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = "SESSION", required = false) String sessionToken) {
        if (sessionToken == null) {
            return ResponseEntity.status(400).build();
        }

        try {
            userService.logout(sessionToken);

            ResponseCookie deleteCookie = ResponseCookie.from("SESSION", "").httpOnly(true).secure(false).path("/").maxAge(0).build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteCookie.toString()).build();
        } catch (AuthException e) {
            return ResponseEntity.status(401).build();
        }
    }
}
