#include "display.c"
#include "lib.c"
#include <malloc.h>
#include <stdio.h>
#include <wctype.h>

const size_t cols = 5;
const size_t rows = 5;

int main() {
    _enable_raw_terminal();
    _clear_terminal();

    double **data = (double**)malloc(sizeof(double*)*rows);

    for (int i = 0; i < rows; i++) {
        data[i] = (double*)malloc(sizeof(double)*cols);
        for (int j = 0; j < cols; j++) {
            data[i][j] = 0;
        }
    }

    Table table;
    table.data = data;
    table.row_capacity = rows;
    table.col_capacity = cols;
    table.cell_width = 15;
    table.rows = rows;
    table.cols = cols;

    table.selected_row = 0;
    table.selected_col = 0;

    _clear_terminal();
    draw_table(&table);

    wint_t c;
    while ((c = readwchar())) {
        if (c == EOF || c == 'q' || c == '') {
            return /* Keyboard Interrupt */ 0;
        }
        if (c == '\n') {
            break;
        }
        if (c == 0b01111111) {
            table.data[table.selected_row][table.selected_col] /= 10;
        }
        if (c == 0b01111110) {
            table.data[table.selected_row][table.selected_col] = 0;
        }
        switch (c) {
            case 'a':
                table.selected_col--;
                break;
            case 's':
                table.selected_row++;
                break;
            case 'w':
                table.selected_row--;
                break;
            case 'd':
                table.selected_col++;
                break;
            case 'r':
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        table.data[i][j] = rand();
                    }
                }
            default:
                if ('0' <= c && c <= '9') {
                    table.data[table.selected_row][table.selected_col] *= 10;
                    table.data[table.selected_row][table.selected_col] += c - '0';
                }
                break;
        }
        _clear_terminal();
        draw_table(&table);
        /*printf("%08b", c);*/
    }


    int res = 0;
    for (int j = 0; j < cols; j++) {
        _Bool unique = 1;
        for (int i = 0; unique && i < rows; i++) {
            for (int k = 0; k < rows; k++) {
                if (i == k) {continue;}
                if (table.data[i][j] == table.data[k][j]) {
                    unique = 0;
                    break;
                }
            }
        }
        if (unique) {res++;}
    }

    printf("Number of columns with unique values: %d\n", res);

    return 0;
}
