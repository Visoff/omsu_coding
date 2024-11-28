#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {
    if (argc != 5) {
        printf("usage: %s <filepath> <count> <lower> <higher>", argv[0]);
        return 1;
    }
    char* path = argv[1];
    int count = atoi(argv[2]);
    int lower = atoi(argv[3]);
    int higher = atoi(argv[4]);
    FILE *f = fopen(path, "w");
    for (int i = 0; i < count; i++) {
        fprintf(f, "%d\n", lower + (rand() % (higher - lower + 1)));
    }
}

