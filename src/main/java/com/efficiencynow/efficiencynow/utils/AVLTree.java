package com.efficiencynow.efficiencynow.utils;

public class AVLTree<K extends Comparable<K>, V> {

    private AVLNode<K, V> root;

    /**
     * Retorna a altura de um nó.
     *
     * @param node O nó cuja altura será retornada.
     * @return A altura do nó, ou 0 se o nó for nulo.
     */
    private int height(AVLNode<K, V> node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Retorna o fator de balanceamento de um nó.
     *
     * @param node O nó cujo fator de balanceamento será retornado.
     * @return O fator de balanceamento do nó.
     */
    private int getBalance(AVLNode<K, V> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    /**
     * Realiza uma rotação à direita em torno de um nó.
     *
     * @param y O nó em torno do qual a rotação será realizada.
     * @return O novo nó raiz após a rotação.
     */
    private AVLNode<K, V> rightRotate(AVLNode<K, V> y) {
        AVLNode<K, V> x = y.left;
        AVLNode<K, V> K2 = x.right;

        x.right = y;
        y.left = K2;

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
    private AVLNode<K, V> leftRotate(AVLNode<K, V> x) {
        AVLNode<K, V> y = x.right;
        AVLNode<K, V> K2 = y.left;

        y.left = x;
        x.right = K2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Insere uma chave e valor na árvore AVL.
     *
     * @param node  O nó raiz da árvore/subárvore onde a chave será inserida.
     * @param key   A chave a ser inserida.
     * @param value O valor associado à chave.
     * @return O novo nó raiz após a inserção.
     */
    public AVLNode<K, V> insert(AVLNode<K, V> node, K key, V value) {
        if (node == null) {
            return new AVLNode<>(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
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
     * Insere uma chave e valor na árvore AVL.
     *
     * @param key   A chave a ser inserida.
     * @param value O valor associado à chave.
     */
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    /**
     * Procura uma chave na árvore AVL.
     *
     * @param key A chave a ser procurada.
     * @return O valor associado à chave, ou null se a chave não for encontrada.
     */
    public V search(K key) {
        return search(root, key);
    }

    /**
     * Procura uma chave na árvore AVL a partir de um nó específico.
     *
     * @param node O nó raiz da árvore/subárvore onde a chave será procurada.
     * @param key  A chave a ser procurada.
     * @return O valor associado à chave, ou null se a chave não for encontrada.
     */
    private V search(AVLNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            return search(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return search(node.right, key);
        } else {
            return node.value;
        }
    }

    /**
     * Remove um nó da árvore AVL pela chave.
     *
     * @param key A chave do nó a ser removido.
     */
    public void delete(K key) {
        root = delete(root, key);
    }

    /**
     * Remove um nó da árvore AVL pela chave.
     *
     * @param node O nó raiz da árvore/subárvore.
     * @param key A chave do nó a ser removido.
     * @return O novo nó raiz após a remoção.
     */
    private AVLNode<K, V> delete(AVLNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                AVLNode<K, V> temp = node.left != null ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                AVLNode<K, V> temp = minValueNode(node.right);
                node.key = temp.key;
                node.value = temp.value;
                node.right = delete(node.right, temp.key);
            }
        }
        if (node == null) {
            return node;
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    /**
     * Encontra o nó com o menor valor em uma subárvore.
     *
     * @param node O nó raiz da subárvore.
     * @return O nó com o menor valor.
     */
    private AVLNode<K, V> minValueNode(AVLNode<K, V> node) {
        AVLNode<K, V> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}
