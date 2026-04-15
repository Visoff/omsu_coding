#pragma once

#include <climits>
#include <iostream>
#include <string>
#include <vector>

class IntBinTree {
private:
    struct Node {
        int val;
        Node *left = nullptr;
        Node *right = nullptr;

        ~Node() {
            delete left;
            delete right;
        }

        Node(const int &val) : val(val) {}
        Node(const Node &node) :
            val(node.val),
            left(node.left == nullptr ? nullptr : new Node(*node.left)),
            right(node.right == nullptr ? nullptr : new Node(*node.right)) {}
    };

    Node *root = nullptr;

    size_t number_of_elements = 0;
    size_t number_of_positive_elements = 0;
    size_t number_of_even_elements = 0;
    size_t sum_of_elements = 0;

    void internal_remove_leafs(Node *);
    const std::string internal_find(Node *ptr, const int &val, const std::string &path) const;
    const bool internal_isBST(Node *ptr, int min, int max) const;
    void flat_tree(Node *ptr, std::vector<std::pair<std::string, size_t>> &flat_array, size_t depth) const;
public:
    IntBinTree() {};
    ~IntBinTree() {delete root;};
    IntBinTree(const IntBinTree &c) : root(c.root == nullptr ? nullptr : new Node(*c.root)) {};
    IntBinTree &operator=(const IntBinTree &rhs) {
        delete root;
        if (rhs.root == nullptr) {
            root = nullptr;
        } else {
            root = new Node(*rhs.root);
        }
        return *this;
    }

    void insert(const int &val, const std::string &path);
    size_t are_all_numbers_even() const {return number_of_even_elements == number_of_elements;};
    size_t are_all_numbers_positive() const {return number_of_positive_elements == number_of_elements;};
    void remove_leafs() {internal_remove_leafs(root);};
    double get_average() const {return static_cast<double>(sum_of_elements) / number_of_elements;};
    const std::string find(const int &val) const {return internal_find(root, val, "");};
    const bool isBST() const {return internal_isBST(root, INT_MIN, INT_MAX);};

    friend std::ostream &operator<<(std::ostream &out, const IntBinTree &tree);
};

std::ostream &operator<<(std::ostream &out, const IntBinTree &tree);
