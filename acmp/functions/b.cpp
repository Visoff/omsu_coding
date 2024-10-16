#include <cstdlib>
#include <stdio.h>

int main() {
    char c;
    int res = 0;
    for (int i = 0; i < 3; i++) {
        scanf("%c", &c);
        if (c == ' ') {i--; continue;}
        if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
            res++;
        }
    }
    printf("%d\n", res);
    return 0;
}

