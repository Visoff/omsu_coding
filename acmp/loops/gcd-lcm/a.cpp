#include <stdio.h>

int main() {
    int a, b;
    scanf("%d %d", &a, &b);
    while (b %= a) {
        a ^= b;
        b ^= a;
        a ^= b;
    }
    printf("%d\n", a);
    return 0;
}

