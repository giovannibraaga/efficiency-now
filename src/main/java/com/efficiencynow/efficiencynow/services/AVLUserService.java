package com.efficiencynow.efficiencynow.services;

import com.efficiencynow.efficiencynow.entities.UserEntity;
import com.efficiencynow.efficiencynow.repositories.UserRepository;
import com.efficiencynow.efficiencynow.utils.AVLTree;
import com.efficiencynow.efficiencynow.utils.UserNode;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciar a árvore AVL de usuários.
 */
@Service
public class AVLUserService {

    private final AVLTree<String, UserNode> userAVLTree = new AVLTree<>();

    @Autowired
    private UserRepository userRepository;

    /**
     * Inicializa a árvore AVL com dados dos usuários do banco de dados.
     */
    @Transactional
    public void initializeTree() {
        try {
            List<UserEntity> users = userRepository.findAll();
            for (UserEntity user : users) {
                UserNode userNode = new UserNode(
                        user.getEmail(),
                        user.getPassword(),
                        user.getName(),
                        user.getId(),
                        null
                );

                userAVLTree.insert(user.getEmail(), userNode);
            }
            System.out.println("AVL Tree inicializada com " + users.size() + " usuários.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar a AVL Tree: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um usuário na árvore AVL pelo email.
     *
     * @param email O email do usuário a ser buscado.
     * @return O nó do usuário encontrado, ou null se não encontrado.
     */
    public UserNode findUserByEmail(String email) {
        return userAVLTree.search(email);
    }

    /**
     * Adiciona um novo usuário na árvore AVL.
     *
     * @param userNode O nó do usuário a ser adicionado.
     */
    public void addUserToAVL(UserNode userNode) {
        userAVLTree.insert(userNode.getEmail(), userNode);
    }

    /**
     * Remove um usuário da árvore AVL pelo email.
     *
     * @param email O email do usuário a ser removido.
     */
    public void removeUserFromAVL(String email) {
        userAVLTree.delete(email);
    }

    /**
     * Configura o serviço, inicializando a árvore AVL após a construção do bean.
     */
    @PostConstruct
    public void setup() {
        initializeTree();
    }
}
