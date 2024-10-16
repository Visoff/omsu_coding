#include <stdio.h>

int calc_avg_in_static_arr(int *arr, size_t len, int *avg) {
    long long sum = 0;
    for (int i = 0; i < len; i++) {
        sum += arr[i];
    }
    *avg = sum/len;
    return 0;
}
