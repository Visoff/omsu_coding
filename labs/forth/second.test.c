#include "second.mod.c"

#define len 3

int main() {
    int arr[len] = {1, 2, 3};
    int avg;
    int res = calc_avg_in_static_arr(arr, len, &avg);
    if (res) {
        printf("ERROR\n");
        return res;
    }
    printf("%d\n", avg);
    return 0;
}
