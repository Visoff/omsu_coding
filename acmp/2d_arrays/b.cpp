#include <cstdlib>
#include <stdio.h>

int main() {
    int n;
    scanf("%d", &n);
    int **matrix = (int**) malloc(sizeof(int*) * n);
    for (int i = 0; i < n; i++) {matrix[i] = (int*) malloc(sizeof(int) * n);}
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            scanf("%d", &matrix[j][i]);
        }
    }

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%d", matrix[i][j]);
            if (j != n - 1) {
                printf(" ");
            } else {
                printf("\n");
            }
        }
        free(matrix[i]);
    }
    free(matrix);
    return 0;
}

