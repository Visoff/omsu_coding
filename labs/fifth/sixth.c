#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>

int main(int argc, char **argv) {
    if (argc != 3) {
        printf("usage: %s <width> <height>\n", argv[0]);
        return 1;
    }
    int width = atoi(argv[1]), height = atoi(argv[2]);
    int **matrix = (int**)malloc(sizeof(int*)*width);
    for (int i = 0; i < width; i++) {
        matrix[i] = (int*)malloc(sizeof(int)*height);
        for (int j = 0; j < height; j++) {
            matrix[i][j] = rand();
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
    return 0;
}
