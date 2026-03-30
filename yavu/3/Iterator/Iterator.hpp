#pragma once

template <typename T>
class Iterator {
public:
    virtual ~Iterator() = default;
    virtual void start() = 0;
    virtual T current() = 0;
    virtual void next() = 0;
    virtual bool done() = 0;
};
