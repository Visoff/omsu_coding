#include <stdio.h>

int main(int argc, char **argv) {
    if (argc != 4) {
        printf("usage: %s <filepath_in_1> <filepath_in_2> <filepath_out>", argv[0]);
    }

    FILE *f_in_1 = fopen(argv[1], "r");
    FILE *f_in_2 = fopen(argv[2], "r");
    FILE *f_out = fopen(argv[3], "w");

    while (1) {
        int v;
        int l = fscanf(f_in_1, "%d", &v);
        if (l == -1) {
            break;
        }
        if (v < 0) {fprintf(f_out, "%d\n", v);}
    }
    while (1) {
        int v;
        int l = fscanf(f_in_2, "%d", &v);
        if (l == -1) {
            break;
        }
        if (v > 0) {fprintf(f_out, "%d\n", v);}
    }
    return 0;
}
