#include <stdio.h>
#include <stdlib.h>
#include "second.mod.c"

int main() {
    int arr[20];
    for (int i = 0; i < 20; i++) {arr[i] = rand();}
    int avg;
    int res = calc_avg_in_static_arr(arr, 20, &avg);
    if (res) {
        printf("ERROR\n");
        return res;
    }
    printf("%d\n", avg);
    return 0;
}
