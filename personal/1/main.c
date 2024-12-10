#include "table.c"
#include <math.h>
#include <stdio.h>

#define PI 3.1415926

const double arg_range_start = -3;
const double arg_range_end = 2;
const double arg_range_step = 0.04;

const int columns = 5;
const int rows = 20;

const int arg_width = 15;
const int arg_presision = 2;
const int res_width = 15;
const int res_presision = 2;

double f(Table *table, double x) {
    if (x > atan(table->p)) {
        return pow(log10(1+fabs(table->p*x)), 2);
    }
    return cos(x*x/(4+table->p))/pow(PI + x*x, 1./3.);
}

_Bool check_p(double p) {
    return fabs(p) <= 5;
}



int main() {
    Table table;
    table.columns = columns;
    table.rows = rows;

    table.start = arg_range_start;
    table.end = arg_range_end;
    table.step = arg_range_step;

    table.func = f;
    table.page = 0;

    table.arg_width = arg_width;
    table.res_width = res_width;

    table.arg_presision = arg_presision;
    table.res_presision = res_presision;

    table.flags = SCIENTIFIC_NOTATION;

    table.check_p = check_p;
    _clear_terminal();
    printf("Введите p: ");
    int status = scanf("%lf", &table.p);
    while (!status || !table.check_p(table.p)) {
        _clear_terminal();
        printf("Введите p: ");
        status = scanf("%lf", &table.p);
    }

    enable_raw_terminal();

    char c;

    while ((c = getchar()) != '\n' && c != EOF) {}

    draw(&table);
    while ((c = getchar()) != EOF) {
        if (c == 'q' || c == '' || c == '') {break;}
        if (c == 'b') {if (table.page > 0) table.page--;}
        if (c == 'e') {
            if (table.flags & SCIENTIFIC_NOTATION) {
                table.flags &= ~SCIENTIFIC_NOTATION;
            } else {
                table.flags |= SCIENTIFIC_NOTATION;
            }
        }
        if (c == 'p') {
            if (table.flags & PRETTY_TABLE) {
                table.flags &= ~PRETTY_TABLE;
            } else {
                table.flags |= PRETTY_TABLE;
            }
        }
        if (c == '\n') {
            table.page++;
            if (table.start + table.step * table.page * table.rows * table.columns > table.end) {break;}
            draw(&table);
            continue;
        }
        if (c == 'c') {
            prompt_for_change(&table);
            continue;
        }
        draw(&table);
    }
    return 0;
}
