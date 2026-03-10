#include "dynarr.hpp"
#include <initializer_list>

DynArray::DynArray() {
    this->_capacity = 1;
    this->size = 0;
    this->data = new int[this->_capacity];
}

DynArray::DynArray(std::initializer_list<int> nums) {
    this->_capacity = nums.size();
    this->size = nums.size();
    this->data = new int[this->_capacity];
    for (size_t i = 0; i < size; ++i)
        data[i] = nums.begin()[i];
}

DynArray::DynArray(int nums[], size_t size) : _capacity(size), size(size) {
    this->data = new int[this->_capacity];
    for (size_t i = 0; i < size; ++i)
        data[i] = nums[i];
}

DynArray::DynArray(Sized, size_t size, int n) : _capacity(size), size(size) {
    this->data = new int[this->_capacity];
    for (size_t i = 0; i < size; ++i)
        data[i] = n;
}

DynArray::DynArray(Sized, size_t size) : DynArray(Sized{}, size, 0) {}

DynArray::DynArray(Reserve, size_t capacity) : _capacity(capacity), size(0) {
    this->data = new int[this->_capacity];
}

DynArray::DynArray(const DynArray& copy) : _capacity(copy._capacity), size(copy.size) {
    this->data = new int[this->_capacity];
    for (size_t i = 0; i < size; ++i)
        data[i] = copy.data[i];
}

DynArray::DynArray(DynArray&& move)
    : data(move.data), _capacity(move._capacity), size(move.size) {
    move.data = nullptr;
    move._capacity = 0;
    move.size = 0;
}

DynArray DynArray::with_capacity(size_t capacity) {
    return DynArray(Reserve{}, capacity);
}

DynArray::~DynArray() {
    delete[] data;
}

int& DynArray::operator[](size_t idx) {
    return data[idx];
}

const int& DynArray::operator[](size_t idx) const {
    return data[idx];
}

size_t DynArray::length() const {
    return size;
}

size_t DynArray::capacity() const {
    return _capacity;
}

void DynArray::resize(size_t new_size) {
    if (new_size > _capacity) {
        int* new_data = new int[new_size];
        for (size_t i = 0; i < size; ++i)
            new_data[i] = data[i];
        delete[] data;
        data = new_data;
        _capacity = new_size;
    }
    if (new_size > size) {
        for (size_t i = size; i < new_size; ++i)
            data[i] = 0;
    }
    size = new_size;
}

void DynArray::reserve(size_t new_capacity) {
    if (new_capacity > _capacity) {
        int* new_data = new int[new_capacity];
        for (size_t i = 0; i < size; ++i)
            new_data[i] = data[i];
        delete[] data;
        data = new_data;
        _capacity = new_capacity;
    }
}

void DynArray::pushBack(int n) {
    if (size == _capacity) {
        size_t new_cap = (_capacity == 0) ? 1 : _capacity * 2;
        reserve(new_cap);
    }
    data[size++] = n;
}

int DynArray::popBack() {
    return data[--size];
}

DynArray& DynArray::operator=(const DynArray& copy) {
    if (this != &copy) {
        delete[] data;
        _capacity = copy._capacity;
        size = copy.size;
        data = new int[_capacity];
        for (size_t i = 0; i < size; ++i)
            data[i] = copy.data[i];
    }
    return *this;
}

DynArray& DynArray::operator=(DynArray&& move) {
    if (this != &move) {
        delete[] data;
        data = move.data;
        _capacity = move._capacity;
        size = move.size;
        move.data = nullptr;
        move._capacity = 0;
        move.size = 0;
    }
    return *this;
}

bool DynArray::operator==(const DynArray& other) const {
    if (size != other.size) return false;
    for (size_t i = 0; i < size; ++i)
        if (data[i] != other.data[i]) return false;
    return true;
}

bool DynArray::operator!=(const DynArray& other) const {
    return !(*this == other);
}

bool DynArray::operator<(const DynArray& other) const {
    size_t min_size = (size < other.size) ? size : other.size;
    for (size_t i = 0; i < min_size; ++i) {
        if (data[i] != other.data[i])
            return data[i] < other.data[i];
    }
    return size < other.size;
}

bool DynArray::operator>(const DynArray& other) const {
    return other < *this;
}

bool DynArray::operator<=(const DynArray& other) const {
    return !(other < *this);
}

bool DynArray::operator>=(const DynArray& other) const {
    return !(*this < other);
}

DynArray DynArray::operator+(const DynArray& other) const {
    DynArray result(Sized{}, size + other.size);
    for (size_t i = 0; i < size; ++i)
        result[i] = data[i];
    for (size_t i = 0; i < other.size; ++i)
        result[size + i] = other.data[i];
    return result;
}

std::ostream& operator<<(std::ostream& out, const DynArray& arr) {
    out << "[";
    for (size_t i = 0; i < arr.size; ++i) {
        out << arr.data[i];
        if (i + 1 < arr.size) out << ", ";
    }
    out << "]";
    return out;
}

std::istream& operator>>(std::istream& in, DynArray& arr) {
    for (size_t i = 0; i < arr.size; ++i)
        in >> arr.data[i];
    return in;
}
