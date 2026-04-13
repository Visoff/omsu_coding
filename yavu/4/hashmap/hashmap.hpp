#pragma once

#include <memory>
#include <optional>
#include "../iterator/iterator.hpp"

template <typename K, typename V>
class HashMap {
private:
    struct HashMapNode {
        K key;
        V value;
        size_t hash;
        HashMapNode* next;
    };

    class IteratorImpl : public Iterator<std::pair<K, V>> {
    private:
        HashMap *map;
        size_t bucket_idx;
        HashMapNode *crnt;

        IteratorImpl(HashMap<K, V> *map) : map(map), bucket_idx(0), crnt(nullptr) {};
    public:
        void start();
        std::pair<K, V> current();
        void next();
        bool done();

        friend class HashMap<K, V>;
    };

    HashMapNode **buckets;
    size_t capacity;
    size_t size;
public:
    HashMap(size_t cap): buckets(new HashMapNode*[cap]), capacity(cap), size(0) {
        for (size_t i = 0; i < cap; i++) {
            buckets[i] = nullptr;
        }
    };
    HashMap(): buckets(nullptr), capacity(0), size(0) {};
    ~HashMap();
    void put(K key, V value);
    std::optional<V> get(K key);
    void remove(K key);
    bool isEmpty();
    void makeEmpty();

    std::unique_ptr<Iterator<std::pair<K, V>>> iterator();
};
