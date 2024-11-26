#include <stdio.h>
#include <malloc.h>

typedef struct {
    double **data;
    size_t row_capacity;
    size_t col_capacity;

    size_t rows;
    size_t cols;

    size_t cell_width;

    size_t selected_row;
    size_t selected_col;
} Table;

/*
┏━┳━┓
┃ ┃ ┃ 
┣━╋━┫
┃ ┃ ┃
┗━┻━┛
\x1b[7m -> selected
*/

typedef enum {
    Left,
    Middle,
    Right
} Side;

void _draw_corner(Side i, Side j) {
    switch (i) {
        case Left:
            switch (j) {
                case Left:
                    printf("┏");
                    break;
                case Middle:
                    printf("┳");
                    break;
                case Right:
                    printf("┓");
                    break;
            }
            break;
        case Middle:
            switch (j) {
                case Left:
                    printf("┣");
                    break;
                case Middle:
                    printf("╋");
                    break;
                case Right:
                    printf("┫");
                    break;
            }
            break;
        case Right:
            switch (j) {
                case Left:
                    printf("┗");
                    break;
                case Middle:
                    printf("┻");
                    break;
                case Right:
                    printf("┛");
                    break;
            }
            break;
    }
}

void _draw_cell(Table *table, size_t i, size_t j) {
    if (table->selected_col == j && table->selected_row == i) {
        printf("\x1b[7m");
    }
    printf("%*.2lf", (int)table->cell_width, table->data[i][j]);
    if (table->selected_col == j && table->selected_row == i) {
        printf("\x1b[0m");
    }
}

void draw_table(Table *table) {
    Side i_side, j_side;
    for (size_t i = 0; i < table->rows; i++) {
        if (i == 0) {i_side = Left;}
        else {i_side = Middle;}

        for (size_t j = 0; j < table->cols; j++) {
            if (j == 0) {j_side = Left;}
            else {j_side = Middle;}

            _draw_corner(i_side, j_side);
            for (int k = 0; k < table->cell_width; k++) {
                printf("━");
            }
        }
        _draw_corner(i_side, Right);
        printf("\n");
        for (size_t j = 0; j < table->cols; j++) {
            printf("┃");
            _draw_cell(table, i, j);
        }
        printf("┃\n");
    }
    _draw_corner(Right, Left);
    for (int k = 0; k < table->cell_width; k++) {
        printf("━");
    }
    for (size_t i = 1; i < table->rows; i++) {
        _draw_corner(Right, Middle);
        for (int k = 0; k < table->cell_width; k++) {
            printf("━");
        }
    }
    _draw_corner(Right, Right);
    printf("\nPress \"r\" for random values and \"enter\" to continue\n");
}
