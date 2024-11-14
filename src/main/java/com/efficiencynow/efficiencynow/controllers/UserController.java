package com.efficiencynow.efficiencynow.controllers;

import com.efficiencynow.efficiencynow.Exceptions.Exceptions.AuthException;
import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.services.AVLUserService;
import com.efficiencynow.efficiencynow.services.UserService;
import com.efficiencynow.efficiencynow.utils.PasswordEncoder;
import com.efficiencynow.efficiencynow.utils.UserNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Controlador REST para gerenciar usuários.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AVLUserService avlUserService;

    @Autowired
    private UserService userService;

    /**
     * Registra um novo usuário.
     *
     * @param userDTO Os dados do usuário a ser registrado.
     * @return ResponseEntity com o usuário registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);

        UserNode userNode = new UserNode(
                registeredUser.getEmail(),
                registeredUser.getName(),
                registeredUser.getId()
        );
        avlUserService.addUserToAVL(userNode);

        registeredUser.setPassword(null);

        return ResponseEntity.status(201).body(registeredUser);
    }

    /**
     * Realiza o login de um usuário.
     *
     * @param userDTO Os dados do usuário para login.
     * @return ResponseEntity com os dados do usuário logado.
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid UserDTO userDTO) {
        try {
            UserDTO loggedUser = userService.login(userDTO);

            ResponseCookie sessionCookie = ResponseCookie.from("SESSION", loggedUser.getToken())
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Lax")
                    .maxAge(7 * 24 * 60 * 60)
                    .path("/")
                    .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, sessionCookie.toString()).body(
                    new UserDTO(null,
                            loggedUser.getName(),
                            loggedUser.getEmail(),
                            null,
                            loggedUser.getToken())
            );
        } catch (AuthException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Obtém o perfil do usuário logado.
     *
     * @param sessionToken O token de sessão do usuário.
     * @return ResponseEntity com os dados do perfil do usuário.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@CookieValue(name = "SESSION", required = false) String sessionToken) {
        if (sessionToken == null || !userService.isAuthenticated(sessionToken)) {
            return ResponseEntity.status(401).build();
        }

        String email = userService.getEmailFromSession(sessionToken);
        UserNode userNode = avlUserService.findUserByEmail(email);

        if (userNode == null) {
            return ResponseEntity.status(404).build();
        }

        UserDTO user = new UserDTO(null, userNode.getName(), userNode.getEmail(), null, sessionToken);
        return ResponseEntity.ok(user);
    }

    /**
     * Encerra a sessão do usuário.
     *
     * @param sessionToken O token de sessão do usuário.
     * @return ResponseEntity indicando o status da operação.
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

    /**
     * Exclui um usuário pelo email.
     *
     * @param email O email do usuário a ser excluído.
     * @return ResponseEntity indicando o status da operação.
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        boolean deleted = userService.deleteUser(email);
        if (deleted) {
            avlUserService.removeUserFromAVL(email);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
