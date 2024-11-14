package com.efficiencynow.efficiencynow.utils;

/**
 * Classe que representa um nó em uma árvore AVL.
 *
 * @param <K> Tipo da chave, que deve ser comparável.
 * @param <V> Tipo do valor armazenado no nó.
 */
public class AVLNode<K extends Comparable<K>, V> {
    K key;
    V value;
    AVLNode<K, V> left, right;
    int height;

    /**
     * Construtor para criar um novo nó AVL.
     *
     * @param key   A chave do nó.
     * @param value O valor armazenado no nó.
     */
    public AVLNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.height = 1;
    }
}