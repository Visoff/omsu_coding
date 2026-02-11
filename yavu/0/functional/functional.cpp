#include <algorithm>
#include <array>
#include <functional>
#include "functional.hpp"

double total_value(const BoxArray& boxes) {
    double total = 0;
    for (const auto& box : boxes) {
        total += box.value;
    }
    return total;
}

bool fits_in_dimensions(const BoxArray& boxes, double max_total_dimensions) {
    double total = 0;
    for (const auto& box : boxes) {
        total += box.length + box.width + box.height;
    }
    return total <= max_total_dimensions;
}

double max_weight_with_volume_less_than(const BoxArray& boxes, int max_volume) {
    double max_weight = 0;
    for (const auto& box : boxes) {
        int volume = box.length * box.width * box.height;
        if (volume <= max_volume && box.weight > max_weight) {
            max_weight = box.weight;
        }
    }
    return max_weight;
}

bool can_boxes_be_nested(const BoxArray& boxes) {
    if (boxes.empty()) return true;
    
    BoxArray sorted_boxes = boxes;
    
    std::sort(sorted_boxes.begin(), sorted_boxes.end(),
        [](const Box& a, const Box& b) {
            auto get_sorted_dims = [](const Box& box) {
                std::array<int, 3> dims = {box.length, box.width, box.height};
                std::sort(dims.begin(), dims.end(), std::greater<int>());
                return dims;
            };
            
            auto dims_a = get_sorted_dims(a);
            auto dims_b = get_sorted_dims(b);
            
            return dims_a > dims_b;
        });
    
    for (size_t i = 0; i < sorted_boxes.size() - 1; ++i) {
        const Box& outer = sorted_boxes[i];
        const Box& inner = sorted_boxes[i + 1];
        
        auto get_sorted_dims = [](const Box& box) {
            std::array<int, 3> dims = {box.length, box.width, box.height};
            std::sort(dims.begin(), dims.end());
            return dims;
        };
        
        auto outer_dims = get_sorted_dims(outer);
        auto inner_dims = get_sorted_dims(inner);
        
        if (!(outer_dims[0] > inner_dims[0] &&
              outer_dims[1] > inner_dims[1] &&
              outer_dims[2] > inner_dims[2])) {
            return false;
        }
    }
    
    return true;
}
