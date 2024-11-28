#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <num>\n", argv[0]);
        return 1;
    }
    int n = atoi(argv[1]);
    int res = 2;
    for (int i = 0; i < n-1; i++) {
        res *= 3;
    }
    printf("%d\n", res);
    return 0;
}
