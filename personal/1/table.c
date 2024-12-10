#include <malloc.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include "display.c"

const uint8_t SCIENTIFIC_NOTATION = 0b1;
const uint8_t PRETTY_TABLE = 0b10;

const char* sides[11] = {
    "┏", "┳", "┓",
    "┣", "╋", "┫",
    "┗", "┻", "┛",
    "┃", "━"
};

typedef enum {
    Top,
    Middle,
    Bottom
} _Horizontal;

typedef struct _Table {
    double (*func)(struct _Table*, double);

    double p;
    _Bool (*check_p)(double);

    double start;
    double end;
    double step;

    uint8_t columns;
    uint8_t rows;

    uint8_t page;

    uint8_t arg_width;
    uint8_t res_width;

    uint8_t arg_presision;
    uint8_t res_presision;

    uint8_t flags;
} Table;

void _draw_pretty_separation_line(Table *table, _Horizontal row) {
    if (row == Top) {
        printf("%s", sides[0]);
    } else if (row == Middle) {
        printf("%s", sides[3]);
    } else {
        printf("%s", sides[6]);
    }
    for (int i = 0; i < table->columns; i++) {
        for (int j = 0; j < table->arg_width; j++) {printf("%s", sides[10]);}
        if (row == Top) {
            printf("%s", sides[1]);
        } else if (row == Middle) {
            printf("%s", sides[4]);
        } else {
            printf("%s", sides[7]);
        }
        for (int j = 0; j < table->res_width; j++) {printf("%s", sides[10]);}
        if (i == table->columns-1) {break;}
        if (row == Top) {
            printf("%s", sides[1]);
        } else if (row == Middle) {
            printf("%s", sides[4]);
        } else {
            printf("%s", sides[7]);
        }
    }
    if (row == Top) {
        printf("%s", sides[2]);
    } else if (row == Middle) {
        printf("%s", sides[5]);
    } else {
        printf("%s", sides[8]);
    }
}

void _draw_seperation_line(Table *table) {
    putchar('!');
    for (int i = 0; i < table->columns; i++) {
        for (int j = 0; j < table->arg_width; j++) {putchar('-');}
        putchar('!');
        for (int j = 0; j < table->res_width; j++) {putchar('-');}
        putchar('!');
    }
}

void _draw_section(size_t width, char *text, size_t len) {
    size_t gap = (width - len) / 2;
    if (gap < 0) {gap = 0;}
    if (width < len) {
        printf("%s", text);
        return;
    }
    for (int i = 0; i < gap; i++) {putchar(' ');}
    printf("%s", text);
    for (int i = 0; i < gap + (width - len) % 2; i++) {putchar(' ');}
}

void _draw_section_double(size_t width, char* format, uint8_t presision, double value) {
    char buf[20];
    int n = sprintf(buf, format, presision, value);
    _draw_section(width, buf, n);
}

void _draw_header(Table *table) {
    if (table->flags & PRETTY_TABLE) {
        _draw_pretty_separation_line(table, Top);
    } else {
        _draw_seperation_line(table);
    }
    printf(" на [%lf; %lf]", table->start, table->end);
    putchar('\n');
    if (table->flags & PRETTY_TABLE) {
        printf("%s", sides[9]);
    } else {
        putchar('!');
    }
    for (int i = 0; i < table->columns; i++) {
        _draw_section(table->arg_width, "Аргумент", 8);
        if (table->flags & PRETTY_TABLE) {
            printf("%s", sides[9]);
        } else {
            putchar('!');
        }
        _draw_section(table->res_width, "Функция", 7);
        if (table->flags & PRETTY_TABLE) {
            printf("%s", sides[9]);
        } else {
            putchar('!');
        }
    }
    printf(" с шагом h = %lf", table->step);
    putchar('\n');
    if (table->flags & PRETTY_TABLE) {
        _draw_pretty_separation_line(table, Middle);
    } else {
        _draw_seperation_line(table);
    }
    printf(" p = %lf", table->p);
    putchar('\n');
}

void _draw_data_row(Table *table, double *arg) {
    if (table->flags & PRETTY_TABLE) {
        printf("%s", sides[9]);
    } else {
        putchar('!');
    }
    for (int i = 0; i < table->columns; i++) {
        if (*arg > table->end) {
            printf("%*s", table->arg_width, "");
            if (table->flags & PRETTY_TABLE) {
                printf("%s", sides[9]);
            } else {
                putchar('!');
            }

            printf("%*s", table->res_width, "");
            if (table->flags & PRETTY_TABLE) {
                printf("%s", sides[9]);
            } else {
                putchar('!');
            }
            return;
        }
        double res = table->func(table, *arg);
        _draw_section_double(table->arg_width, "%.*lf", table->arg_presision, *arg);
        if (table->flags & PRETTY_TABLE) {
            printf("%s", sides[9]);
        } else {
            putchar('!');
        }
        if (table->flags & SCIENTIFIC_NOTATION) {
            _draw_section_double(table->res_width, "%.*e", table->res_presision, res);
        } else {
            _draw_section_double(table->res_width, "%.*lf", table->res_presision, res);
        }
        if (table->flags & PRETTY_TABLE) {
            printf("%s", sides[9]);
        } else {
            putchar('!');
        }
        *arg += table->step;
    }
}

void prompt_for_change(Table *table) {
    _clear_terminal();
    printf("c(columns): %d\n", table->columns);
    printf("r(ows): %d\n", table->rows);
    printf("a(rgs)\n");
    printf("f(unction)\n");
    int c = getchar();
    if (c == 'c') {
        reset_terminal();
        printf("columns: ");
        int val;
        scanf("%d", &val);
        table->columns = val;
        enable_raw_terminal();
    } else if (c == 'r') {
        reset_terminal();
        printf("rows: ");
        int val;
        scanf("%d", &val);
        table->rows = val;
        enable_raw_terminal();
    } else if (c == 'a') {
        _clear_terminal();
        printf("w(arg width): %d\n", table->arg_width);
        printf("p(arg presision): %d\n", table->arg_presision);
        c = getchar();
        if (c == 'w') {
            reset_terminal();
            _clear_terminal();
            printf("arg width: ");
            int val;
            scanf("%d", &val);
            table->arg_width = val;
            enable_raw_terminal();
        } else if (c == 'p') {
            reset_terminal();
            _clear_terminal();
            printf("arg presision: ");
            int val;
            scanf("%d", &val);
            table->arg_presision = val;
            enable_raw_terminal();
        }
    } else if (c == 'f') {
        _clear_terminal();
        printf("w(function width): %d\n", table->res_width);
        printf("p(function resision): %d\n", table->res_presision);
        c = getchar();
        if (c == 'w') {
            reset_terminal();
            _clear_terminal();
            printf("res width: ");
            int val;
            scanf("%d", &val);
            table->res_width = val;
            enable_raw_terminal();
        } else if (c == 'p') {
            reset_terminal();
            _clear_terminal();
            printf("res presision: ");
            int val;
            scanf("%d", &val);
            table->res_presision = val;
            enable_raw_terminal();
        }
    }
    while ((c = getchar()) != '\n' && c != EOF) {}
}

void draw(Table *table) {
    _clear_terminal();
    _draw_header(table);
    double arg = table->start + table->step * table->page * table->rows * table->columns;
    int i = 0;
    while (arg <= table->end && i++ < table->rows) {
        _draw_data_row(table, &arg);
        putchar('\n');
        if (arg > table->end || i >= table->rows) {break;}
        if (table->flags & PRETTY_TABLE) {
            _draw_pretty_separation_line(table, Middle);
        } else {
            _draw_seperation_line(table);
        }
        putchar('\n');
    }
    if (table->flags & PRETTY_TABLE) {
        _draw_pretty_separation_line(table, Bottom);
    } else {
        _draw_seperation_line(table);
    }
    putchar('\n');

    char buf[128];
    sprintf(buf, "Page %d/%d | Press Enter to continue | Press e to switch scientific notation",
            table->page+1,
            (int)((table->end-table->start)/(table->step*table->rows*table->columns) + 1));
    _draw_section((table->arg_width + table->res_width + 2)*table->columns, buf, strlen(buf));
    putchar('\n');
}

