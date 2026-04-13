#include "linkedhashmap.hpp"

template <typename K, typename V>
LinkedHashMap<K, V>::~LinkedHashMap() {
    if (this->buckets == nullptr) {return;}
    makeEmpty();
    delete[] this->buckets;
}

template <typename K, typename V>
void LinkedHashMap<K, V>::makeEmpty() {
    for (size_t i = 0; i < this->capacity; i++) {
        HashMapNode* current = this->buckets[i];
        while (current != nullptr) {
            HashMapNode* next = current->next;
            delete current;
            current = next;
        }
    }
    this->size = 0;
    this->last_in_order = nullptr;
    this->first_in_order = nullptr;
}

template <typename K, typename V>
void LinkedHashMap<K, V>::put(K key, V value) {
    if (this->capacity == 0) {
        this->capacity = 5;
        if (this->buckets != nullptr) {
            delete[] this->buckets;
        }
        this->buckets = new HashMapNode*[this->capacity];
        for (size_t i = 0; i < this->capacity; i++) {
            this->buckets[i] = nullptr;
        }
    }
    if (this->size > 0.8 * this->capacity) {
        LinkedHashMap<K, V> new_map(this->capacity*2);
        std::unique_ptr<Iterator<std::pair<K, V>>> it = this->iterator();
        it->start();
        while (!it->done()) {
            std::pair<K, V> pair = it->current();
            new_map.put(pair.first, pair.second);
            it->next();
        }
        this->makeEmpty();
        delete[] this->buckets;
        this->capacity *= 2;
        this->buckets = new_map.buckets;
        new_map.buckets = nullptr;
        this->size = new_map.size;
    }
    HashMapNode *node = new HashMapNode;
    node->key = key;
    node->value = value;
    node->hash = std::hash<K>{}(key);
    node->next = nullptr;
    node->prev_ord = nullptr;
    node->next_ord = nullptr;
    size_t bucket = node->hash % this->capacity;
    HashMapNode *current = this->buckets[bucket];
    if (current == nullptr) {
        this->buckets[bucket] = node;
    } else {
        while (current->next != nullptr) {
            if (current->hash == node->hash && current->key == node->key) {
                current->value = node->value;
                delete node;
                return;
            }
            current = current->next;
        }
        current->next = node;
    }
    this->size++;

    if (this->last_in_order == nullptr) {
        this->last_in_order = node;
        this->first_in_order = node;
    } else {
        this->last_in_order->next_ord = node;
        node->prev_ord = this->last_in_order;
        this->last_in_order = node;
    }
}

template <typename K, typename V>
std::optional<V> LinkedHashMap<K, V>::get(K key) {
    if (this->size == 0) return std::nullopt;
    size_t hash = std::hash<K>{}(key);
    size_t bucket = hash % this->capacity;
    HashMapNode* current = this->buckets[bucket];
    while (current != nullptr) {
        if (current->hash == hash && current->key == key) {
            return current->value;
        }
        current = current->next;
    }
    return std::nullopt;
}

template <typename K, typename V>
void LinkedHashMap<K, V>::remove(K key) {
    size_t hash = std::hash<K>{}(key);
    size_t bucket = hash % this->capacity;
    HashMapNode* current = this->buckets[bucket];
    if (current == nullptr) {
        return;
    }
    if (current->hash == hash && current->key == key) {
        this->buckets[bucket] = current->next;
        if (current->prev_ord == nullptr) {
            this->first_in_order = current->next_ord;
        }
        if (current->next_ord == nullptr) {
            this->last_in_order = current->prev_ord;
        }
        if (current->prev_ord != nullptr) {
            current->prev_ord->next_ord = current->next_ord;
        }
        if (current->next_ord != nullptr) {
            current->next_ord->prev_ord = current->prev_ord;
        }
        delete current;
    } else {
        HashMapNode* prev = current;
        current = current->next;
        while (current != nullptr) {
            if (current->hash == hash && current->key == key) {
                prev->next = current->next;
                if (current->prev_ord == nullptr) {
                    this->first_in_order = current->next_ord;
                }
                if (current->next_ord == nullptr) {
                    this->last_in_order = current->prev_ord;
                }
                if (current->prev_ord != nullptr) {
                    current->prev_ord->next_ord = current->next_ord;
                }
                if (current->next_ord != nullptr) {
                    current->next_ord->prev_ord = current->prev_ord;
                }
                delete current;
                break;
            }
            prev = current;
            current = current->next;
        }
    }
    this->size--;
}

template <typename K, typename V>
bool LinkedHashMap<K, V>::isEmpty() {
    return this->size == 0;
}

template <typename K, typename V>
std::unique_ptr<Iterator<std::pair<K, V>>> LinkedHashMap<K, V>::iterator() {
    return std::unique_ptr<Iterator<std::pair<K, V>>>(new IteratorImpl(this));
}

template <typename K, typename V>
void LinkedHashMap<K, V>::IteratorImpl::start() {
    this->crnt = this->strt;
}

template <typename K, typename V>
void LinkedHashMap<K, V>::IteratorImpl::next() {
    this->crnt = this->crnt->next_ord;
}

template <typename K, typename V>
std::pair<K, V> LinkedHashMap<K, V>::IteratorImpl::current() {
    return std::pair<K, V>(this->crnt->key, this->crnt->value);
}

template <typename K, typename V>
bool LinkedHashMap<K, V>::IteratorImpl::done() {
    return this->crnt == nullptr;
}
