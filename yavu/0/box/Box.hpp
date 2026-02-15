#pragma once
#include <iostream>

class Box {
    private:
        int length, width, height;
        double weight;
        int value;
    public:
        Box(int length = 0, int width = 0, int height = 0, double weight = 0.0,
            int value = 0);
        bool operator==(const Box &other) const;
        friend std::istream &operator>>(std::istream &is, Box &box);
        friend std::ostream &operator<<(std::ostream &os, const Box &box);

        int get_length() const;
        int get_width() const;
        int get_height() const;
        double get_weight() const;
        int get_value() const;

        void set_length(int length);
        void set_width(int width);
        void set_height(int height);
        void set_weight(double weight);
        void set_value(int value);
};
