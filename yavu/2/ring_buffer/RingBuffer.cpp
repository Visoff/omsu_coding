#include "RingBuffer.hpp"

#include <stdexcept>

template <typename T>
size_t RingBuffer<T>::index(const size_t i) const {
    return (start + i) % capacity;
}

template <typename T>
void RingBuffer<T>::resize(const size_t new_capacity) {
    T *new_data = new T[new_capacity];
    for (size_t i = 0; i < length; ++i) {
        new_data[i] = data[index(i)];
    }
    delete[] data;
    data = new_data;
    start = 0;
    capacity = new_capacity;
}

template <typename T>
RingBuffer<T>::RingBuffer() : capacity(1), length(0), start(0), data(new T[1]) {}

template <typename T>
RingBuffer<T>::RingBuffer(size_t capacity) : capacity(capacity), length(0), start(0), data(new T[capacity]) {}

template <typename T>
RingBuffer<T>::~RingBuffer() {
    delete[] data;
}

template <typename T>
void RingBuffer<T>::push_back(const T &element) {
    if (length == capacity) {
        resize(capacity * 2);
    }
    data[index(length)] = element;
    ++length;
}

template <typename T>
void RingBuffer<T>::push_front(const T &element) {
    if (length == capacity) {
        resize(capacity * 2);
    }
    start = (start - 1 + capacity) % capacity;
    data[start] = element;
    ++length;
}

template <typename T>
T RingBuffer<T>::pop_front() {
    if (length == 0) {
        throw std::out_of_range("ring buffer is empty");
    }
    T element = data[start];
    start = (start + 1) % capacity;
    --length;
    return element;
}

template <typename T>
T RingBuffer<T>::pop_back() {
    if (length == 0) {
        throw std::out_of_range("ring buffer is empty");
    }
    --length;
    return data[index(length)];
}

template <typename T>
size_t RingBuffer<T>::size() const {
    return length;
}

template <typename T>
bool RingBuffer<T>::is_empty() const {
    return length == 0;
}

template <typename T>
void RingBuffer<T>::clear() {
    length = 0;
    start = 0;
}

template <typename T>
T &RingBuffer<T>::front() const {
    if (length == 0) {
        throw std::out_of_range("ring buffer is empty");
    }
    return data[start];
}

template <typename T>
T &RingBuffer<T>::back() const {
    if (length == 0) {
        throw std::out_of_range("ring buffer is empty");
    }
    return data[index(length - 1)];
}

template <typename T>
T &RingBuffer<T>::operator[](const size_t i) const {
    if (i >= length) {
        throw std::out_of_range("index out of range");
    }
    return data[index(i)];
}
