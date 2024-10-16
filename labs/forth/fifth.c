#include "forth.mod.c"
#include <stdlib.h>
#include <stdio.h>
#include <malloc.h>

int main() {
    size_t w, h;
    scanf("%ld %ld", &w, &h);
    int **matrix = (int**)malloc(sizeof(int*)*w);
    for (int i = 0; i < w; i++) {
        matrix[i] = (int*)malloc(sizeof(int)*h);
    }
    fill_2d_arr_with_sum(matrix, w, h);
    for (int i = 0; i < w; i++) {
        for (int j = 0; j < h; j++) {
            printf("%2d ", matrix[i][j]);
        }
        printf("\n");
    }
    return 0;
}
