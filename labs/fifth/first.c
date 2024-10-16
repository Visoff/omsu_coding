#include <stdio.h>
#include <stdlib.h>

int itoa(char *s) {
    int res = 0;
    for (; s[0] != 0; s++) {
        res = res*10 + s[0] - '0';
    }
    return res;
}

int main(int argc, char **argv) {
    if (argc != 5) {
        printf("usage: %s <filepath> <count> <lower> <higher>", argv[0]);
        return 1;
    }
    char* path = argv[1];
    int count = itoa(argv[2]);
    int lower = itoa(argv[3]);
    int higher = itoa(argv[4]);
    FILE *f = fopen(path, "w");
    for (int i = 0; i < count; i++) {
        fprintf(f, "%d\n", lower + (rand() % (higher - lower + 1)));
    }
}
