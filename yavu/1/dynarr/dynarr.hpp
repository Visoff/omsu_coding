#pragma once

#include <cstddef>
#include <initializer_list>
#include <iostream>

class DynArray {
private:
    int* data;
    size_t _capacity;
    size_t size;

public:
    struct Reserve {};
    struct Sized {};

    DynArray();
    DynArray(int nums[], size_t size);
    DynArray(std::initializer_list<int> nums);
    DynArray(Sized, size_t size, int n);
    DynArray(Sized, size_t size);
    DynArray(Reserve, size_t capacity);
    DynArray(const DynArray& copy);
    DynArray(DynArray&& move);

    static DynArray with_capacity(size_t capacity);

    ~DynArray();

    int& operator[](size_t idx);
    const int& operator[](size_t idx) const;

    size_t length() const;
    size_t capacity() const;

    void resize(size_t new_size);
    void reserve(size_t new_capacity);
    void pushBack(int n);
    int popBack();

    DynArray& operator=(const DynArray& copy);
    DynArray& operator=(DynArray&& move);

    bool operator==(const DynArray& other) const;
    bool operator!=(const DynArray& other) const;
    bool operator<(const DynArray& other) const;
    bool operator>(const DynArray& other) const;
    bool operator<=(const DynArray& other) const;
    bool operator>=(const DynArray& other) const;

    DynArray operator+(const DynArray& other) const;

    friend std::ostream& operator<<(std::ostream& out, const DynArray& arr);
    friend std::istream& operator>>(std::istream& in, DynArray& arr);
};
