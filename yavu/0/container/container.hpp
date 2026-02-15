#pragma once

#include <vector>
#include "../box/Box.hpp"

class DoesNotFitException : public std::exception {};

class Container {
    private:
        std::vector<Box> boxes;
        int length, width, height;
        double max_weight;

    public:
        /**
         * @description: creates an *empty* container
        **/
        Container(int length, int width, int height, double max_weight);

        /**
         * @description: push box to the end
        **/
        void add_box(Box box, int idx);
        int add_box(Box box) noexcept(false);

        Box get_box(int idx) const;
        Box remove_box(int idx);

        int count() const; // num of boxes
        double total_weight() const;
        double total_value() const;

        int get_length() const;
        int get_width() const;
        int get_height() const;
        double get_max_weight() const;

        void set_length(int length);
        void set_width(int width);
        void set_height(int height);
        void set_max_weight(double max_weight);

        friend std::ostream &operator<<(std::ostream &os, const Container &container);
        friend std::istream &operator>>(std::istream &is, Container &container);

        Box &operator[](int idx);
        const Box &operator[](int idx) const;
};
