#include <cstdlib>
#include <stdio.h>

int main() {
    char c;
    int res = 0;
    for (int i = 0; i < 3; i++) {
        scanf("%c", &c);
        if (c == ' ') {i--; continue;}
        if ('0' <= c && c <= '9') {
            res++;
        }
    }
    printf("%d\n", res);
    return 0;
}

