#include "hashmap.hpp"

template <typename K, typename V>
HashMap<K, V>::~HashMap() {
    if (this->buckets == nullptr) {return;}
    makeEmpty();
    delete[] this->buckets;
}

template <typename K, typename V>
void HashMap<K, V>::makeEmpty() {
    for (size_t i = 0; i < this->capacity; i++) {
        HashMapNode* current = this->buckets[i];
        while (current != nullptr) {
            HashMapNode* next = current->next;
            delete current;
            current = next;
        }
    }
    this->size = 0;
}

template <typename K, typename V>
void HashMap<K, V>::put(K key, V value) {
    if (this->capacity == 0 || this->buckets == nullptr) {
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
        HashMap<K, V> new_map(this->capacity*2);
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
    size_t bucket = node->hash % this->capacity;
    HashMapNode *current = this->buckets[bucket];
    if (current == nullptr) {
        this->buckets[bucket] = node;
    } else {
        while (current != nullptr) {
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
}

template <typename K, typename V>
std::optional<V> HashMap<K, V>::get(K key) {
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
void HashMap<K, V>::remove(K key) {
    size_t hash = std::hash<K>{}(key);
    size_t bucket = hash % this->capacity;
    HashMapNode* current = this->buckets[bucket];
    if (current == nullptr) {
        return;
    }
    if (current->hash == hash && current->key == key) {
        this->buckets[bucket] = current->next;
        delete current;
    } else {
        HashMapNode* prev = current;
        current = current->next;
        while (current != nullptr) {
            if (current->hash == hash && current->key == key) {
                prev->next = current->next;
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
bool HashMap<K, V>::isEmpty() {
    return this->size == 0;
}

template <typename K, typename V>
std::unique_ptr<Iterator<std::pair<K, V>>> HashMap<K, V>::iterator() {
    return std::unique_ptr<Iterator<std::pair<K, V>>>(new IteratorImpl(this));
}

template <typename K, typename V>
std::pair<K, V> HashMap<K, V>::IteratorImpl::current() {
    return std::pair<K, V>(this->crnt->key, this->crnt->value);
}
template <typename K, typename V>
void HashMap<K, V>::IteratorImpl::start() {
    bucket_idx = 0;
    crnt = nullptr;
    if (!map->buckets || map->capacity == 0) return;
    while (bucket_idx < map->capacity && map->buckets[bucket_idx] == nullptr) {
        ++bucket_idx;
    }
    if (bucket_idx < map->capacity) {
        crnt = map->buckets[bucket_idx];
    }
}

template <typename K, typename V>
void HashMap<K, V>::IteratorImpl::next() {
    if (crnt) crnt = crnt->next;
    while (crnt == nullptr && bucket_idx + 1 < map->capacity) {
        ++bucket_idx;
        crnt = map->buckets[bucket_idx];
    }
    if (crnt == nullptr) bucket_idx = map->capacity;
}

template <typename K, typename V>
bool HashMap<K, V>::IteratorImpl::done() {
    return crnt == nullptr && bucket_idx >= map->capacity;
}
