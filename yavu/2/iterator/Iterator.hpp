#pragma once

#include "../ring_buffer/RingBuffer.hpp"

template <typename T>
class Iterator {
private:
    const RingBuffer<T> &data;
    size_t idx;

public:
    Iterator(const RingBuffer<T> &data);

    void start();
    void next();
    bool finish();
    T getValue();
};
