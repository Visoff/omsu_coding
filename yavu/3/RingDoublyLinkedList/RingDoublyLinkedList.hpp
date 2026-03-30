#pragma once

#include "../List/List.hpp"
#include <cstddef>
#include <memory>

template<typename T>
class RingDoublyLinkedList : public List<T> {
private:
    struct Node {
        T value;
        Node* next;
        Node* prev;
    };

    class IteratorImpl : public Iterator<T> {
    private:
        Node *head;
        Node *curr;
        IteratorImpl(Node *head, Node *curr);
    public:
        void start() override;
        T current() override;
        void next() override;
        bool done() override;

        friend class RingDoublyLinkedList<T>;
    };

    Node* head;
    size_t length;
public:
    RingDoublyLinkedList();
    RingDoublyLinkedList(List<T> &list);
    ~RingDoublyLinkedList();
    void insert_in_place(std::unique_ptr<Iterator<T>> &it, T value) override;
    T delete_in_place(std::unique_ptr<Iterator<T>> &it) override;
    std::unique_ptr<Iterator<T>> find(T value) override;
    void clear() override;
    bool is_empty() override {return this->length == 0;}
    size_t size() override {return this->length;}
    std::unique_ptr<Iterator<T>> iterate() override;
};
