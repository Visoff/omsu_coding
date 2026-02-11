#pragma once
#include <iostream>

struct Box {
  int length, width, height;
  double weight;
  int value;

  Box(int length = 0, int width = 0, int height = 0, double weight = 0.0,
      int value = 0);

  bool operator==(const Box &other) const;
  friend std::istream &operator>>(std::istream &is, Box &box);
  friend std::ostream &operator<<(std::ostream &os, const Box &box);
};
