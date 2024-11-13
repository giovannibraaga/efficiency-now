package com.efficiencynow.efficiencynow.utils;

public class AVLTree<T extends Comparable<T>> {

    private AVLNode<T> root;

    /**
     * Retorna a altura de um nó.
     *
     * @param node O nó cuja altura será retornada.
     * @return A altura do nó, ou 0 se o nó for nulo.
     */
    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Retorna o fator de balanceamento de um nó.
     *
     * @param node O nó cujo fator de balanceamento será retornado.
     * @return O fator de balanceamento do nó.
     */
    private int getBalance(AVLNode<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    /**
     * Realiza uma rotação à direita em torno de um nó.
     *
     * @param y O nó em torno do qual a rotação será realizada.
     * @return O novo nó raiz após a rotação.
     */
    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * Realiza uma rotação à esquerda em torno de um nó.
     *
     * @param x O nó em torno do qual a rotação será realizada.
     * @return O novo nó raiz após a rotação.
     */
    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Insere uma chave na árvore AVL.
     *
     * @param node O nó raiz da árvore/subárvore onde a chave será inserida.
     * @param key  A chave a ser inserida.
     * @return O novo nó raiz após a inserção.
     */
    public AVLNode<T> insert(AVLNode<T> node, T key) {
        if (node == null) {
            return new AVLNode<>(key);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Insere uma chave na árvore AVL.
     *
     * @param key A chave a ser inserida.
     */
    public void insert(T key) {
        root = insert(root, key);
    }

    /**
     * Procura uma chave na árvore AVL.
     *
     * @param key A chave a ser procurada.
     * @return true se a chave for encontrada, false caso contrário.
     */
    public boolean search(T key) {
        return search(root, key);
    }

    /**
     * Procura uma chave na árvore AVL a partir de um nó específico.
     *
     * @param node O nó raiz da árvore/subárvore onde a chave será procurada.
     * @param key  A chave a ser procurada.
     * @return true se a chave for encontrada, false caso contrário.
     */
    private boolean search(AVLNode<T> node, T key) {
        if (node == null) {
            return false;
        }

        if (key.compareTo(node.key) < 0) {
            return search(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return search(node.right, key);
        } else {
            return true;
        }
    }
}
