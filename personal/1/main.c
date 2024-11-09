#include <malloc.h>
#include <stdint.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <stdlib.h>

#define args_col_width 10
#define func_col_width 15

typedef struct {
    double (*func)(double);

    double p;
    _Bool (*check_p)(double);

    double start;
    double end;
    double step;

    uint8_t columns;
    uint8_t rows;

    uint8_t page;
} Table;

void _clear_terminal() {
    printf("\e[1;1H\e[2J");
}

void _draw_seperation_line(Table *table) {
    putchar('!');
    for (int i = 0; i < table->columns; i++) {
        for (int j = 0; j < args_col_width; j++) {putchar('-');}
        putchar('!');
        for (int j = 0; j < func_col_width; j++) {putchar('-');}
        putchar('!');
    }
}

void _draw_section(size_t width, char *text, size_t len) {
    size_t gap = (width - len) / 2;
    if (gap < 0) {gap = 0;}
    for (int i = 0; i < gap; i++) {putchar(' ');}
    printf("%s", text);
    for (int i = 0; i < gap + (width - len) % 2; i++) {putchar(' ');}
}

void _draw_section_double(size_t width, char* format, double value) {
    char buf[20];
    int n = sprintf(buf, format, value);
    _draw_section(width, buf, n);
}

void _draw_header(Table *table) {
    _draw_seperation_line(table);
    putchar('\n');
    putchar('!');
    for (int i = 0; i < table->columns; i++) {
        _draw_section(args_col_width, "Аргумент", 8);
        putchar('!');
        _draw_section(func_col_width, "Функция", 7);
        putchar('!');
    }
    printf(" на [%lf; %lf], с шагом h = %lf; p = %lf", table->start, table->end, table->step, table->step);
    putchar('\n');
    _draw_seperation_line(table);
    putchar('\n');
}

void _draw_data_row(Table *table, double *arg) {
    putchar('!');
    for (int i = 0; i < table->columns; i++) {
        if (*arg > table->end) {
            printf("%*s%*s", args_col_width+1, "!", func_col_width+1, "!");
            return;
        }
        double res = table->func(*arg);
        _draw_section_double(args_col_width, "%.4lf", *arg);
        putchar('!');
        _draw_section_double(func_col_width, "%.4e", res);
        putchar('!');
        *arg += table->step;
    }
}

void draw(Table *table) {
    _clear_terminal();
    _draw_header(table);
    double arg = table->start + table->step * table->page * table->rows * table->columns;
    int i = 0;
    while (arg <= table->end && i++ < table->rows) {
        _draw_data_row(table, &arg);
        putchar('\n');
        _draw_seperation_line(table);
        putchar('\n');
    }
    _draw_section((func_col_width + args_col_width + 2) * table->columns, "Press ENTER", 13);
    putchar('\n');
}

static struct termios default_tty;

void reset_terminal() {
    tcsetattr(STDIN_FILENO, TCSANOW, &default_tty);
}

void enable_raw_terminal() {
    tcgetattr(STDIN_FILENO, &default_tty);
    atexit(reset_terminal);
    struct termios tty = default_tty;
    tty.c_lflag &= ~(ECHO | ICANON);
    tcsetattr(STDIN_FILENO, TCSANOW, &tty);
}

double f(double x) {
    return x * x;
}

_Bool check_p(double p) {
    return p > 0;
}

int main() {
    Table table;
    table.columns = 2;
    table.rows = 5;

    table.start = 0;
    table.end = 10;
    table.step = 1;

    table.func = f;
    table.page = 0;

    table.check_p = check_p;
    while (1) {
        _clear_terminal();
        printf("Введите p: ");
        scanf("%lf", &table.p);
        if (table.check_p(table.p)) {break;}
    }

    enable_raw_terminal();

    char c;

    while ((c = getchar()) != '\n' && c != EOF) {}

    draw(&table);
    while ((c = getchar()) != EOF) {
        if (c == 'q' || c == '' || c == '') {break;}
        if (c == '\n') {
            table.page++;
            if (table.start + table.step * table.page * table.rows * table.columns > table.end) {break;}
            draw(&table);
            continue;
        }
        draw(&table);
    }
    return 0;
}
