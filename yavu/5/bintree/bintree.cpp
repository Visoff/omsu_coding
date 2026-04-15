#include "bintree.hpp"
#include <stdexcept>
#include <string>

void IntBinTree::insert(const int &val, const std::string &path) {
    if (root == nullptr) {
        if (path.size() != 0) {
            throw std::runtime_error("Invalid path");
        }
        root = new Node(val);
        root->left = nullptr;
        root->right = nullptr;
    }
    Node *curr = root;
    for (size_t i = 0; i < path.size(); i++) {
        char c = path[i];
        if (c == '0') {
            if (curr->left == nullptr) {
                if (i == path.size() - 1) {
                    curr->left = new Node(val);
                } else {
                    throw std::runtime_error("Invalid path");
                }
            }
            curr = curr->left;
        } else {
            if (curr->right == nullptr) {
                if (i == path.size() - 1) {
                    curr->right = new Node(val);
                } else {
                    throw std::runtime_error("Invalid path");
                }
            }
            curr = curr->right;
        }
    }
    curr->val = val;
    this->number_of_elements++;
    this->sum_of_elements += val;
    if (val > 0) {
        this->number_of_positive_elements++;
    }
    if (val % 2 == 0) {
        this->number_of_even_elements++;
    }
}

void IntBinTree::internal_remove_leafs(Node *ptr) {
    if (ptr == root && ptr->left == nullptr && ptr->right == nullptr) {
        delete ptr;
        root = nullptr;
        number_of_elements = 0;
        number_of_positive_elements = 0;
        number_of_even_elements = 0;
        sum_of_elements = 0;
        return;
    }
    if (ptr->left) {
        if (ptr->left->left == nullptr && ptr->left->right == nullptr) {
            number_of_elements--;
            sum_of_elements -= ptr->left->val;
            if (ptr->left->val > 0) {
                number_of_positive_elements--;
            }
            if (ptr->left->val % 2 == 0) {
                number_of_even_elements--;
            }
            delete ptr->left;
            ptr->left = nullptr;
        } else {
            internal_remove_leafs(ptr->left);
        }
    }
    if (ptr->right) {
        if (ptr->right->left == nullptr && ptr->right->right == nullptr) {
            number_of_elements--;
            sum_of_elements -= ptr->right->val;
            if (ptr->right->val > 0) {
                number_of_positive_elements--;
            }
            if (ptr->right->val % 2 == 0) {
                number_of_even_elements--;
            }
            delete ptr->right;
            ptr->right = nullptr;
        } else {
            internal_remove_leafs(ptr->right);
        }
    }
}

const std::string IntBinTree::internal_find(Node *ptr, const int &val, const std::string &path) const {
    if (ptr == nullptr) {
        return "";
    }
    if (ptr->val == val) {
        return path;
    }
    std::string left = internal_find(ptr->left, val, path + "0");
    if (left != "") {
        return left;
    }
    return internal_find(ptr->right, val, path + "1");
}

const bool IntBinTree::internal_isBST(Node *ptr, int min, int max) const {
    if (ptr == nullptr) {
        return true;
    }
    if (ptr->val < min || ptr->val > max) {
        return false;
    }
    // left if inclucive
    return internal_isBST(ptr->left, min, ptr->val) && internal_isBST(ptr->right, ptr->val + 1, max);
}

void IntBinTree::flat_tree(Node *ptr, std::vector<std::pair<std::string, size_t>> &flat_array, size_t depth) const {
    if (ptr == nullptr) {
        return;
    }
    flat_tree(ptr->left, flat_array, depth + 1);
    flat_array.push_back(std::make_pair(std::to_string(ptr->val), depth));
    flat_tree(ptr->right, flat_array, depth + 1);
}

std::ostream &operator<<(std::ostream &out, const IntBinTree &tree) {
    std::vector<std::pair<std::string, size_t>> flat_array;
    tree.flat_tree(tree.root, flat_array, 0);
    std::cout << flat_array.size() << std::endl;
    size_t depth = 0;
    bool work = true;
    std::string s;
    while (work) {
        work = false;
        s = "";
        for (size_t i = 0; i < flat_array.size(); i++) {
            if (flat_array[i].second == depth) {
                work = true;
                s += flat_array[i].first;
            } else {
                for (size_t j = 0; j < flat_array[i].first.size(); j++) {
                    s += " ";
                }
            }
            s += " ";
        }
        if (work) {
            out << s << std::endl;
        }
        depth++;
    }
    return out;
}
