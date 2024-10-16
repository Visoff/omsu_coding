#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>

int main(int argc, char **argv) {
    if (argc != 4) {
        printf("usage: %s <filepath>", argv[0]);
        return 1;
    }
    char *filepath = argv[1];
    FILE *f = fopen(filepath, "r");
    int width, height;
    fscanf(f, "%d %d", &width, &height);
    int **matrix = (int**)malloc(sizeof(int*)*height);
    for (int i = 0; i < height; i++) {
        matrix[i] = (int*)malloc(sizeof(int)*width);
        for (int j = 0; j < width; j++) {
            int l = fscanf(f, "%d", &matrix[i][j]);
            if (l == -1) {
                printf("Error not enough values\n");
                return 1;
            }
        }
    }
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}
