package com.efficiencynow.efficiencynow.utils;

/**
 * Nó da árvore AVL.
 *
 * @param <T> O tipo de dados que o nó armazena, que deve ser comparável.
 */
class AVLNode<T extends Comparable<T>> {
    /**
     * A chave armazenada no nó.
     */
    T key;

    /**
     * A altura do nó na árvore.
     */
    int height;

    /**
     * Referência para o nó filho à esquerda.
     */
    AVLNode<T> left;

    /**
     * Referência para o nó filho à direita.
     */
    AVLNode<T> right;

    /**
     * Construtor para criar um nó com uma chave específica.
     *
     * @param key A chave a ser armazenada no nó.
     */
    public AVLNode(T key) {
        this.key = key;
        this.height = 1;
    }
}
