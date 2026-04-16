#pragma once

#include <cstddef>
#include <string>
#include <vector>
#include <iostream>

class Dictionary {
private:
    struct Node {
        std::string word;
        size_t count;
        Node *left;
        Node *right;

        size_t height;

        Node(const std::string &word) : word(word), count(1), left(nullptr), right(nullptr), height(1) {}
        Node(const Node &other) : word(other.word), count(other.count), left(nullptr), right(nullptr), height(other.height) {
            if (other.left != nullptr) {
                left = new Node(*other.left);
            }
            if (other.right != nullptr) {
                right = new Node(*other.right);
            }
        }
        ~Node() {
            delete left;
            delete right;
        }

        static Node *left_rotate(Node *node);
        static Node *right_rotate(Node *node);
        static Node *insert(Node *node, const std::string &word);
        static Node *remove(Node *node, const std::string &word, bool &found);
        static void in_order_traverse(Node *node, std::vector<std::pair<std::string, size_t>> &words);
    };

    Node *root;
    size_t num_words;
public:
    Dictionary() : root(nullptr), num_words(0) {};
    ~Dictionary() {
        delete root;
    }
    Dictionary(const Dictionary &other) : root(nullptr), num_words(other.num_words) {
        if (other.root != nullptr) {
            root = new Node(*other.root);
        }
    };
    Dictionary(Dictionary &&other) : root(other.root), num_words(other.num_words) {other.root = nullptr; other.num_words = 0;}
    Dictionary &operator=(const Dictionary &other) {
        root = nullptr;
        if (other.root != nullptr) {
            root = new Node(*other.root);
        }
        num_words = other.num_words;
        return *this;
    }
    Dictionary &operator=(Dictionary &&other) {
        std::swap(root, other.root);
        std::swap(num_words, other.num_words);
        return *this;
    }

    size_t count(const std::string &word) const;
    void add(const std::string &word) {
        root = Node::insert(root, word);
        num_words++;
    }
    void remove(const std::string &word) {
        bool found = false;
        root = Node::remove(root, word, found);
        if (found) num_words--;
    }
    size_t size() const {return  num_words;}

    friend std::ostream &operator<<(std::ostream &os, const Dictionary &dictionary);
};

std::ostream &operator<<(std::ostream &os, const Dictionary &dictionary);
