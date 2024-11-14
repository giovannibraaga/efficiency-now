package com.efficiencynow.efficiencynow.services;

import com.efficiencynow.efficiencynow.Exceptions.Exceptions.AuthException;
import com.efficiencynow.efficiencynow.Exceptions.Exceptions.DuplicateEmailException;
import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.entities.UserEntity;
import com.efficiencynow.efficiencynow.repositories.UserRepository;
import com.efficiencynow.efficiencynow.utils.PasswordEncoder;
import com.efficiencynow.efficiencynow.utils.Populator;
import com.efficiencynow.efficiencynow.utils.UserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço para gerenciar operações relacionadas a usuários.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AVLUserService avlUserService;

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
     * Realiza o login de um usuário.
     *
     * @param userDTO O DTO do usuário contendo email e senha.
     * @return O objeto UserDTO do usuário logado, contendo o token de sessão.
     * @throws AuthException Se o email ou a senha forem inválidos.
     */
    public UserDTO login(UserDTO userDTO) {
        UserNode userNode = avlUserService.findUserByEmail(userDTO.getEmail());
        if (userNode == null || !PasswordEncoder.checkPassword(userDTO.getPassword(), userNode.getPasswordHash())) {
            throw new AuthException("E-mail ou senha inválidos.");
        }

        String token = UUID.randomUUID().toString();

        userNode.setToken(token);
        userDTO.setToken(token);

        storeSessionToken(token, userNode.getEmail());
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

    /**
     * Exclui um usuário pelo email.
     *
     * @param email O email do usuário a ser excluído.
     * @return true se o usuário foi excluído com sucesso, false caso contrário.
     */
    @Transactional
    public boolean deleteUser(String email) {
        try {
            userRepository.deleteByEmail(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Armazena um token de sessão.
     *
     * @param token O token de sessão.
     * @param email O email do usuário associado ao token de sessão.
     */
    public void storeSessionToken(String token, String email) {
        activeSessions.put(token, email);
    }
}
