#include <cstdlib>
#include <stdio.h>

int main() {
    int n, m;
    scanf("%d %d", &n, &m);
    int **matrix = (int**) malloc(sizeof(int*) * n);
    for (int i = 0; i < n; i++) {
        matrix[i] = (int*) malloc(sizeof(int) * m);
        for (int j = 0; j < m; j++) {
            scanf("%d", &matrix[i][j]);
        }
    }
    for (int i = 0; i < n; i++) {
        int s = 0;
        for (int j = 0; j < m; j++) {
            s += matrix[i][j];
        }
        printf("%d", s);
        if (i != n - 1) {
            printf(" ");
        } else {
            printf("\n");
        }
    }

    for (int j = 0; j < m; j++) {
        int s = 0;
        for (int i = 0; i < n; i++) {
            s += matrix[i][j];
        }
        printf("%d", s);
        if (j != m - 1) {
            printf(" ");
        } else {
            printf("\n");
        }
    }

    printf("\n");

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            printf("%d", matrix[i][j]);
            if (j != m - 1) {
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

