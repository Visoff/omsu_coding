#include <stdio.h>

int atoi(char *s) {
    int res = 0;
    int sign = 1;
    if (s[0] == '-') {
        sign = -1;
        s++;
    }
    while (s[0] != '\0') {
        res = res * 10 + s[0] - '0';
        s++;
    }
    return sign*res;
}

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
