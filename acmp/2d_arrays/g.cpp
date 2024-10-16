#include <cstdlib>
#include <stdio.h>

typedef struct {
    int n;
    int m;
    int **data;
} Matrtix;

Matrtix create_matrix(int n, int m) {
    int **data = (int**) malloc(n * sizeof(int*));
    for (int i = 0; i < n; i++) {
        data[i] = (int*) malloc(m * sizeof(int));
    }

    return {n, m, data};
}

Matrtix read_matrix(int n, int m) {
    Matrtix matrix = create_matrix(n, m);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            scanf("%d", &matrix.data[i][j]);
        }
    }

    return matrix;
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
    int n, m, p;
    scanf("%d %d %d", &n, &m, &p);

    Matrtix a = read_matrix(n, m);
    Matrtix b = read_matrix(m, p);

    Matrtix res_matrix = create_matrix(n, p);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < p; j++) {
            int res = 0;
            for (int k = 0; k < m; k++) {
                res += a.data[i][k] * b.data[k][j];
            }
            res_matrix.data[i][j] = res;
        }
    }

    print_matrix(res_matrix);
    free_matrix(a);
    free_matrix(b);
    free_matrix(res_matrix);

    return 0;
}

