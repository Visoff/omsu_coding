#include "lib.c"
#include <stdio.h>
#include <stdlib.h>

int main() {
    double **data = (double**)malloc(sizeof(double*)*3);

    for (int i = 0; i < 3; i++) {
        data[i] = (double*)malloc(sizeof(double)*3);
        for (int j = 0; j < 3; j++) {
            data[i][j] = 0;
        }
    }

    Table table;
    table.data = data;
    table.rows = 3;
    table.cols = 3;

    table.data[0][1] = 1;
    table.data[1][1] = -1;
    
    lab_task(&table);

    if (table.data[0][1] != 0) {
        printf("Test failed\n");
        exit(1);
    }

    printf("All tests passed\n");
}
