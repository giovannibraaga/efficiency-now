package com.efficiencynow.efficiencynow.services;

import com.efficiencynow.efficiencynow.Exceptions.Exceptions.AuthException;
import com.efficiencynow.efficiencynow.Exceptions.Exceptions.DuplicateEmailException;
import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.entities.UserEntity;
import com.efficiencynow.efficiencynow.repositories.UserRepository;
import com.efficiencynow.efficiencynow.utils.PasswordEncoder;
import com.efficiencynow.efficiencynow.utils.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço para gerenciar operações relacionadas a usuários.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private ConcurrentHashMap<String, String> activeSessions = new ConcurrentHashMap<>();

    /**
     * Registra um novo usuário.
     *
     * @param userDTO Os dados do usuário a ser registrado.
     * @return O objeto UserDTO do usuário registrado.
     */
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        UserEntity userEntity = Populator.toEntity(userDTO);

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("O email fornecido já está em uso. Escolha outro para continuar o cadastro.");
        }

        userEntity.setPassword(PasswordEncoder.encodePassword(userDTO.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);
        return Populator.toModel(savedUser);
    }

    /**
     * Encontra um usuário pelo email.
     *
     * @param email O email do usuário a ser encontrado.
     * @return Um Optional contendo o UserDTO do usuário encontrado, ou vazio se não encontrado.
     */
    public Optional<UserDTO> findByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userEntity.map(Populator::toModel);
    }


    /**
     * Realiza o login de um usuário.
     *
     * @param email    O email do usuário.
     * @param password A senha do usuário.
     * @return O objeto UserDTO do usuário logado, contendo o token de sessão.
     * @throws AuthException Se o email ou a senha forem inválidos.
     */
    public UserDTO login(String email, String password) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new AuthException("E-mail ou senha inválidos.");
        }

        UserEntity userEntity = userOptional.get();

        if (!PasswordEncoder.checkPassword(password, userEntity.getPassword())) {
            throw new AuthException("E-mail ou senha inválidos.");
        }

        String sessionToken = UUID.randomUUID().toString();

        activeSessions.put(sessionToken, userEntity.getEmail());

        UserDTO userDTO = Populator.toModel(userEntity);
        userDTO.setToken(sessionToken);

        return userDTO;
    }

    /**
     * Obtém o email associado a um token de sessão.
     *
     * @param sessionToken O token de sessão.
     * @return O email associado ao token de sessão, ou null se o token não for encontrado.
     */
    public String getEmailFromSession(String sessionToken) {
        return activeSessions.get(sessionToken);
    }

    /**
     * Encerra a sessão de um usuário.
     *
     * @param sessionToken O token de sessão a ser encerrado.
     * @throws AuthException Se o token de sessão for inválido ou já estiver encerrado.
     */
    public void logout(String sessionToken) {
        if (activeSessions.containsKey(sessionToken)) {
            activeSessions.remove(sessionToken);
        } else {
            throw new AuthException("Sessão inválida ou já encerrada.");
        }
    }

    /**
     * Verifica se um token de sessão é válido.
     *
     * @param sessionToken O token de sessão a ser verificado.
     * @return true se o token de sessão for válido, false caso contrário.
     */
    public boolean isAuthenticated(String sessionToken) {
        return activeSessions.containsKey(sessionToken);
    }
}