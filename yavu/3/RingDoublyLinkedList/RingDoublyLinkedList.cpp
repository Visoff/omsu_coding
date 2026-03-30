#include "RingDoublyLinkedList.hpp"

template <typename T>
RingDoublyLinkedList<T>::RingDoublyLinkedList() {
    length = 0;
    head = new Node();
    head->prev = head;
    head->next = head;
}

template <typename T>
RingDoublyLinkedList<T>::RingDoublyLinkedList(List<T> &other)
    : RingDoublyLinkedList() {
    auto it = other.iterate();
    while (!it->done()) {
        insert_in_place(it, it->current());
        it->next();
    }
}

template <typename T>
RingDoublyLinkedList<T>::~RingDoublyLinkedList() {
    clear();
    delete head;
}

template <typename T>
RingDoublyLinkedList<T>::IteratorImpl::IteratorImpl(Node *head, Node *curr)
    : head(head), curr(curr) {}

template <typename T>
void RingDoublyLinkedList<T>::IteratorImpl::start() {
    curr = head->next;
}

template <typename T>
T RingDoublyLinkedList<T>::IteratorImpl::current() {
    if (done()) {
        throw "Iterator is done";
    }
    return curr->value;
}

template <typename T>
void RingDoublyLinkedList<T>::IteratorImpl::next() {
    curr = curr->next;
}

template <typename T>
bool RingDoublyLinkedList<T>::IteratorImpl::done() {
    return curr == head;
}

template <typename T>
void RingDoublyLinkedList<T>::insert_in_place(std::unique_ptr<Iterator<T>> &it, T value) {
    IteratorImpl *impl = static_cast<IteratorImpl*>(it.get());

    Node *new_node = new Node();
    new_node->value = value;
    new_node->prev = impl->curr;
    new_node->next = impl->curr->next;

    impl->curr->next = new_node;
    new_node->next->prev = new_node;

    ++length;
}

template <typename T>
T RingDoublyLinkedList<T>::delete_in_place(std::unique_ptr<Iterator<T>> &it) {
    IteratorImpl *impl = static_cast<IteratorImpl*>(it.get());

    if (impl->done()) {
        throw "Cannot delete the sentinel";
    }

    T value = impl->curr->value;

    impl->curr->prev->next = impl->curr->next;
    impl->curr->next->prev = impl->curr->prev;

    Node *next = impl->curr->next;
    delete impl->curr;
    impl->curr = next;

    --length;
    return value;
}

template <typename T>
std::unique_ptr<Iterator<T>> RingDoublyLinkedList<T>::find(T value) {
    Node *ptr = head->next;
    while (ptr != head) {
        if (ptr->value == value) {
            // Use explicit new to avoid private constructor access issue
            return std::unique_ptr<IteratorImpl>(new IteratorImpl(head, ptr));
        }
        ptr = ptr->next;
    }
    return std::unique_ptr<IteratorImpl>(new IteratorImpl(head, head));
}

template <typename T>
void RingDoublyLinkedList<T>::clear() {
    Node *ptr = head->next;
    while (ptr != head) {
        Node *next = ptr->next;
        delete ptr;
        ptr = next;
    }
    head->next = head;
    head->prev = head;
    length = 0;
}

template <typename T>
std::unique_ptr<Iterator<T>> RingDoublyLinkedList<T>::iterate() {
    return std::unique_ptr<IteratorImpl>(new IteratorImpl(head, head->next));
}
