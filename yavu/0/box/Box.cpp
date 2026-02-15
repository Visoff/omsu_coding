#include "Box.hpp"

Box::Box(int length, int width, int height, double weight, int value)
    : length(length), width(width), height(height), weight(weight),
      value(value) {}

bool Box::operator==(const Box &other) const {
  return length == other.length && width == other.width &&
         height == other.height && weight == other.weight &&
         value == other.value;
}

std::ostream &operator<<(std::ostream &os, const Box &box) {
  os << box.length << " " << box.width << " " << box.height << " " << box.weight
     << " " << box.value;
  return os;
}

std::istream &operator>>(std::istream &is, Box &box) {
  int value;
  is >> box.length >> box.width >> box.height >> box.weight >> value;
  box.value = value;
  return is;
}


int Box::get_length() const { return length; }
int Box::get_width() const { return width; }
int Box::get_height() const { return height; }
double Box::get_weight() const { return weight; }
int Box::get_value() const { return value; }

void Box::set_length(int length) { this->length = length; }
void Box::set_width(int width) { this->width = width; }
void Box::set_height(int height) { this->height = height; }
void Box::set_weight(double weight) { this->weight = weight; }
void Box::set_value(int value) { this->value = value; }
