#pragma once

#include "../Iterator/Iterator.hpp"
#include <cstddef>
#include <memory>

template <typename T>
class List {
public:
    virtual ~List() = default;
    virtual void insert_in_place(std::unique_ptr<Iterator<T>> &it, T value) = 0;
    virtual T delete_in_place(std::unique_ptr<Iterator<T>> &it) = 0;
    virtual std::unique_ptr<Iterator<T>> find(T value) = 0;
    virtual void clear() = 0;
    virtual bool is_empty() = 0;
    virtual size_t size() = 0;
    virtual std::unique_ptr<Iterator<T>> iterate() = 0;
};
