#include <stdio.h>

int main() {
    FILE *f_in = fopen("in1.txt", "r");
    FILE *f_out1 = fopen("out1.txt", "w");
    FILE *f_out2 = fopen("out2.txt", "w");

    while (1) {
        char c;
        int l = fscanf(f_in, "%c", &c);
        if (l == -1) {
            break;
        }
        if ('0' <= c && c <= '9') {
            fprintf(f_out2, "%c", c);
        } else {
            fprintf(f_out1, "%c", c);
        }
    }
    return 0;
}
