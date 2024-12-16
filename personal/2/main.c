#include "display.c"
#include "lib.c"
#include <malloc.h>
#include <stddef.h>
#include <stdio.h>
#include <wctype.h>

const size_t cols = 5;
const size_t rows = 5;

const size_t col_capacity = 100;
const size_t row_capacity = 100;

int main(int argc, char *argv[]) {
    _enable_raw_terminal();
    _clear_terminal();

    /*
    double raw_data[row_capacity][col_capacity];
    double *data[row_capacity];
    for (int i = 0; i < row_capacity; i++) {
        data[i] = &raw_data[i][0];
    }
    */

    double **data = (double**)malloc(sizeof(double*)*row_capacity);

    for (int i = 0; i < row_capacity; i++) {
        data[i] = (double*)malloc(sizeof(double)*col_capacity);
        for (int j = 0; j < col_capacity; j++) {
            data[i][j] = 0;
        }
    }

    if (argc > 1) {
        FILE *f = fopen(argv[1], "r");
        if (f != NULL) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char bytes[sizeof(double)];
                    for (int i = 0; i < sizeof(double); i++) {
                        bytes[i] = fgetc(f);
                    }
                    data[i][j] = *(double *)bytes;
                }
            }
        }
    }

    Table table;
    table.data = data;
    table.row_capacity = row_capacity;
    table.col_capacity = col_capacity;
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
                for (int i = 0; i < table.rows; i++) {
                    for (int j = 0; j < table.cols; j++) {
                        table.data[i][j] = rand();
                    }
                }
                break;
            case '-':
                table.data[table.selected_row][table.selected_col]*=-1;
                break;
            case 'c':
                _clear_terminal();
                printf("c(olumns): %d/%d\n", (int)table.cols, (int)table.col_capacity);
                printf("r(ows): %d/%d\n", (int)table.rows, (int)table.row_capacity);
                printf("w(idth): %d\n", (int)table.cell_width);
                c = readwchar();
                _reset_terminal();
                int val;
                switch (c) {
                    case 'c':
                        printf("colums (%d/%d): ", (int)table.cols, (int)table.col_capacity);
                        scanf("%d", &val);
                        table.cols = (size_t)val;
                        break;
                    case 'r':
                        printf("rows (%d/%d): ", (int)table.rows, (int)table.row_capacity);
                        scanf("%d", &val);
                        table.rows = (size_t)val;
                        break;
                    case 'w':
                        printf("width (%d): ", (int)table.cell_width);
                        scanf("%d", &val);
                        table.cell_width = (size_t)val;
                        break;
                    default:
                        break;
                }
                _enable_raw_terminal();
                char ch;
                while ((ch = getchar()) != '\n' && ch != EOF);
                break;
            case 'l':
                _clear_terminal();
                printf("Enter file name: ");
                _reset_terminal();
                char filename[100];
                scanf("%s", filename);
                FILE *f = fopen(filename, "w");
                for (int i = 0; i < table.rows; i++) {
                    for (int j = 0; j < table.cols; j++) {
                        char bytes[sizeof(double)];
                        *(double *)bytes = table.data[i][j];
                        for (int i = 0; i < sizeof(double); i++) {
                            fputc(bytes[i], f);
                        }
                    }
                }
                break;
            case 't':
                lab_task(&table);
                break;
            default:
                if ('0' <= c && c <= '9') {
                    table.data[table.selected_row][table.selected_col] *= 10;
                    table.data[table.selected_row][table.selected_col] += c - '0';
                }
                break;
        }
        _clear_terminal();
        draw_table(&table);
    }

    return 0;
}
