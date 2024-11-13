package com.efficiencynow.efficiencynow.controllers;

import com.efficiencynow.efficiencynow.Exceptions.Exceptions;
import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            UserDTO user = userService.login(userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exceptions.AuthException e) {
            return ResponseEntity.status(401).body(null);
        }
    }
}
