#define COL1_WIDTH 5
#define COL2_WIDTH 15
#define COL3_WIDTH 13

#include <stdio.h>
#include <string.h>

void horizontal_line() {
    for (int i = 0; i < 4+COL1_WIDTH+COL2_WIDTH+COL3_WIDTH; i++) {
        printf("-");
    }
    printf("\n");
}

void print_cell(char* s, int width) {
    printf(" %s%*s", s, (int)(width - strlen(s) - 1), "");
}

void print_row(char* c1, char* c2, char* c3) {
    printf("|");
    print_cell(c1, COL1_WIDTH);
    printf("|");
    print_cell(c2, COL2_WIDTH);
    printf("|");
    print_cell(c3, COL3_WIDTH);
    printf("|");
    printf("\n");
}

int main() {
    horizontal_line();
    // ASCII troubles
    print_row("№", "Выражение", "Результат");
    horizontal_line();
    int x = 2, y = 5;
    char buf[1024];
    sprintf(buf, "%d", 2/5);
    print_row("1.", "z=2/5", buf);
    sprintf(buf, "%.1lf", 2./5);
    print_row("2.", "z=2./5", buf);
    sprintf(buf, "%.1lf", 2/5.);
    print_row("3.", "z=2/5.", buf);
    sprintf(buf, "%d", x/5);
    print_row("4.", "z=x/5", buf);
    sprintf(buf, "%.1lf", x/5.);
    print_row("5.", "z=x/5.", buf);
    sprintf(buf, "%d", x/y);
    print_row("6.", "z=x/y", buf);
    sprintf(buf, "%.1lf", (double)x/y);
    print_row("7.", "z=double(x)/y", buf);
    sprintf(buf, "%.1lf", x/(double)y);
    print_row("8.", "z=x/double(y)", buf);
    sprintf(buf, "%d", 2%5);
    print_row("9.", "z=2%5", buf);
    sprintf(buf, "%d", y%x);
    print_row("10.", "z=y%x", buf);
    horizontal_line();
    return 0;
}
