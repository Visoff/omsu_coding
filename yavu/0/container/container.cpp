#include "container.hpp"

Container::Container(int length, int width, int height, double max_weight) {
    this->length = length;
    this->width = width;
    this->height = height;
    this->max_weight = max_weight;
}

void Container::add_box(Box box, int idx) {
    this->boxes.insert(this->boxes.begin() + idx, box);
}

int Container::add_box(Box box) noexcept(false) {
    if (this->total_weight() + box.get_weight() > this->max_weight)
        throw DoesNotFitException();
    this->boxes.push_back(box);
    return this->count() - 1;
}

Box Container::get_box(int idx) const {
    return this->boxes[idx];
}

Box Container::remove_box(int idx) {
    Box box = this->boxes[idx];
    this->boxes.erase(this->boxes.begin() + idx);
    return box;
}

int Container::count() const {
    return this->boxes.size();
}

double Container::total_weight() const {
    double weight = 0;
    for (const Box &box : this->boxes)
        weight += box.get_weight();
    return weight;
}

double Container::total_value() const {
    double value = 0;
    for (const Box &box : this->boxes)
        value += box.get_value();
    return value;
}

int Container::get_length() const {
    return this->length;
}

int Container::get_width() const {
    return this->width;
}

int Container::get_height() const {
    return this->height;
}

double Container::get_max_weight() const {
    return this->max_weight;
}

void Container::set_length(int length) {
    this->length = length;
}

void Container::set_width(int width) {
    this->width = width;
}

void Container::set_height(int height) {
    this->height = height;
}

void Container::set_max_weight(double max_weight) {
    this->max_weight = max_weight;
}

Box &Container::operator[](int idx) {
    return this->boxes[idx];
}

const Box &Container::operator[](int idx) const {
    return this->boxes[idx];
}

std::istream &operator>>(std::istream &is, Container &container) {
    int n;
    is >> container.length >> container.width >> container.height >>
       container.max_weight >> n;
    for (int i = 0; i < n; i++) {
        Box box;
        is >> box;
        container.add_box(box);
    }
    return is;
}

std::ostream &operator<<(std::ostream &os, const Container &container) {
    os << container.length << " " << container.width << " " << container.height
       << " " << container.max_weight << " " << container.count() << "\n";
    for (const Box &box : container.boxes)
        os << box << "\n";
    return os;
}
