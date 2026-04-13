#pragma once

#include <memory>
#include <optional>
#include <utility>
#include "../iterator/iterator.hpp"

template <typename K, typename V>
class LinkedHashMap {
private:
    struct HashMapNode {
        K key;
        V value;
        size_t hash;
        HashMapNode *next;
        HashMapNode *next_ord;
        HashMapNode *prev_ord;
    };

    class IteratorImpl : public Iterator<std::pair<K, V>> {
    private:
        HashMapNode *strt;
        HashMapNode *crnt;
        IteratorImpl(LinkedHashMap<K, V> *map) : strt(map->first_in_order), crnt(nullptr) {};
    public:
        void start();
        void next();
        std::pair<K, V> current();
        bool done();


        friend class LinkedHashMap<K, V>;
    };

    HashMapNode **buckets;
    size_t capacity;
    size_t size;
    HashMapNode *last_in_order;
    HashMapNode *first_in_order;
public:
    LinkedHashMap(size_t cap): buckets(new HashMapNode*[cap]), capacity(cap), size(0) {
        for (size_t i = 0; i < cap; i++) {
            buckets[i] = nullptr;
        }
    };
    LinkedHashMap(): buckets(nullptr), capacity(0), size(0) {};
    ~LinkedHashMap();
    void put(K key, V value);
    std::optional<V> get(K key);
    void remove(K key);
    bool isEmpty();
    void makeEmpty();

    std::unique_ptr<Iterator<std::pair<K, V>>> iterator();
};
