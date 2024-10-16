#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>

char *num_to_bytes(long long num) {
    char *res = malloc(sizeof(char)*(sizeof(num)+1));
    for (int i = 0; i < sizeof(num); i++) {
        res[i] = (num << (i*8)) & 0xFF;
    }
    res[sizeof(num)] = 0;
    return res;
}

int main(int argc, char **argv) {
    if (argc != 3) {
        printf("usage: %s <filepath> <number>\n", argv[0]);
        return 1;
    }
    char *filepath = argv[1];
    int n = atoi(argv[2]);
    FILE *f = fopen(filepath, "w");

    long long ptr = 1;
    for (int i = 0; i < n; i++) {
        char *s = num_to_bytes(ptr);
        fprintf(f, "%s", s);
        free(s);
        ptr <<= 1;
    }
    return 0;
}
