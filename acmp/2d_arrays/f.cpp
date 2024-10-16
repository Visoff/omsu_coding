#include <cstdlib>
#include <stdio.h>

typedef struct {
    int n;
    int m;
    int **data;
} Matrtix;

Matrtix read_matrix(int n, int m) {
    int **data = (int**) malloc(n * sizeof(int*));
    for (int i = 0; i < n; i++) {
        data[i] = (int*) malloc(m * sizeof(int));
        for (int j = 0; j < m; j++) {
            scanf("%d", &data[i][j]);
        }
    }

    return {n, m, data};
}

void free_matrix(Matrtix matrix) {
    for (int i = 0; i < matrix.n; i++) {
        free(matrix.data[i]);
    }
    free(matrix.data);
}

void print_matrix(Matrtix matrix) {
    for (int i = 0; i < matrix.n; i++) {
        for (int j = 0; j < matrix.m; j++) {
            printf("%d", matrix.data[i][j]);
            if (j != matrix.m - 1) {
                printf(" ");
            } else {
                printf("\n");
            }
        }
    }
}

int main() {
    int n, m;
    scanf("%d %d", &n, &m);

    Matrtix a = read_matrix(n, m);
    Matrtix b = read_matrix(n, m);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            a.data[i][j] += b.data[i][j];
        }
    }

    print_matrix(a);
    free_matrix(a);
    free_matrix(b);

    return 0;
}

