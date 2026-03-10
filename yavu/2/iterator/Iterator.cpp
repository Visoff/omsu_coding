#include "Iterator.hpp"

template <typename T>
Iterator<T>::Iterator(const RingBuffer<T> &data) : data(data), idx(0) {}

template <typename T>
void Iterator<T>::start() {
    idx = 0;
}

template <typename T>
void Iterator<T>::next() {
    idx++;
}

template <typename T>
bool Iterator<T>::finish() {
    return idx == data.size();
}

template <typename T>
T Iterator<T>::getValue() {
    return data[idx];
}
