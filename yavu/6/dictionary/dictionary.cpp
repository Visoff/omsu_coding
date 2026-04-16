#include "dictionary.hpp"

#define height(n) ((n) ? (n)->height : 0)

Dictionary::Node *Dictionary::Node::left_rotate(Node *node) {
  Node *r = node->right;
  Node *rl = r->left;

  r->left = node;
  node->right = rl;

  node->height = 1 + std::max(height(node->left), height(node->right));
  r->height = 1 + std::max(height(r->left), height(r->right));

  return r;
}

Dictionary::Node *Dictionary::Node::right_rotate(Node *node) {
  Node *l = node->left;
  Node *lr = l->right;

  l->right = node;
  node->left = lr;

  node->height = 1 + std::max(height(node->left), height(node->right));
  l->height = 1 + std::max(height(l->left), height(l->right));

  return l;
}

size_t Dictionary::count(const std::string &word) const {
  Node *current = root;
  while (current != nullptr) {
    if (current->word == word) {
      return current->count;
    } else if (word < current->word) {
      current = current->left;
    } else {
      current = current->right;
    }
  }
  return 0;
}

Dictionary::Node *Dictionary::Node::insert(Node *node,
                                           const std::string &word) {
  if (node == nullptr) {
    return new Node(word);
  }

  if (word < node->word) {
    node->left = insert(node->left, word);
  } else if (word > node->word) {
    node->right = insert(node->right, word);
  } else {
    node->count++;
    return node;
  }

  node->height = 1 + std::max(height(node->left), height(node->right));

  int balance = height(node->left) - height(node->right);

  if (balance > 1 && word < node->left->word) {
    return right_rotate(node);
  }

  if (balance < -1 && word > node->right->word) {
    return left_rotate(node);
  }

  if (balance > 1 && word > node->left->word) {
    node->left = left_rotate(node->left);
    return right_rotate(node);
  }

  if (balance < -1 && word < node->right->word) {
    node->right = right_rotate(node->right);
    return left_rotate(node);
  }

  return node;
}

Dictionary::Node *Dictionary::Node::remove(Node *node, const std::string &word,
                                           bool &found) {
  if (node == nullptr) {
    return nullptr;
  }

  if (word < node->word) {
    node->left = remove(node->left, word, found);
  } else if (word > node->word) {
    node->right = remove(node->right, word, found);
  } else if (word == node->word) {
    found = true;
    if (node->count > 1) {
      node->count--;
      return node;
    }

    if (node->left == nullptr) {
      Node *rightChild = node->right;
      node->right = nullptr;
      delete node;
      return rightChild;
    } else if (node->right == nullptr) {
      Node *leftChild = node->left;
      node->left = nullptr;
      delete node;
      return leftChild;
    } else {
      Node *successor = node->right;
      while (successor->left != nullptr) {
        successor = successor->left;
      }

      node->word = successor->word;
      node->count = successor->count;

      node->right = remove(node->right, successor->word, found);
    }
  }

  node->height = 1 + std::max(height(node->left), height(node->right));

  int balance = height(node->left) - height(node->right);

  if (balance > 1 && height(node->left->left) >= height(node->left->right)) {
    return right_rotate(node);
  }
  if (balance > 1 && height(node->left->left) < height(node->left->right)) {
    node->left = left_rotate(node->left);
    return right_rotate(node);
  }
  if (balance < -1 && height(node->right->right) >= height(node->right->left)) {
    return left_rotate(node);
  }
  if (balance < -1 && height(node->right->right) < height(node->right->left)) {
    node->right = right_rotate(node->right);
    return left_rotate(node);
  }

  return node;
}

void Dictionary::Node::in_order_traverse(
    Node *node, std::vector<std::pair<std::string, size_t>> &words) {
  if (node == nullptr)
    return;
  Node::in_order_traverse(node->left, words);
  words.push_back({node->word, node->count});
  Node::in_order_traverse(node->right, words);
}

std::ostream &operator<<(std::ostream &os, const Dictionary &dictionary) {
  os << dictionary.num_words << " ";
  std::vector<std::pair<std::string, size_t>> words;
  Dictionary::Node::in_order_traverse(dictionary.root, words);
  for (const auto &word : words) {
    os << word.first << " " << word.second << " ";
  }
  os << std::endl;
  return os;
}
