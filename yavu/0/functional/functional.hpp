#pragma once
#include "../box/Box.hpp"
#include <vector>

using BoxArray = std::vector<Box>;

// 2
double total_value(const BoxArray& boxes);

// 3
bool fits_in_dimensions(const BoxArray& boxes, double max_total_dimensions);

// 4
double max_weight_with_volume_less_than(const BoxArray& boxes, int max_volume);

// 5
bool can_boxes_be_nested(const BoxArray& boxes);
