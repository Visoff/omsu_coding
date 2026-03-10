#pragma once

#include <cstddef>

template <typename T>
class RingBuffer {
private:
    size_t capacity;
    size_t length;
    size_t start;
    T *data;

    size_t index(const size_t i) const;
    void resize(const size_t new_capacity);

public:
    RingBuffer();
    RingBuffer(size_t capacity);
    ~RingBuffer();

    void push_back(const T &element);
    void push_front(const T &element);
    T pop_front();
    T pop_back();

    size_t size() const;
    bool is_empty() const;
    void clear();

    T &front() const;
    T &back() const;

    T &operator[](const size_t i) const;
};
