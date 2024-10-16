#include <stdio.h>

int main() {
    FILE *f_in = fopen("in.txt", "r");
    FILE *f_out = fopen("out.txt", "w");

    while (1) {
        int v;
        int l = fscanf(f_in, "%d", &v);
        if (l == -1) {
            break;
        }
        if (v % 3 == 0) {fprintf(f_out, "%d\n", v);}
    }
    return 0;
}
