#include <stdio.h>

int main() {
    long long x, p, y;
    scanf("%lld %lld %lld", &x, &p, &y);
    x *= 100;
    y *= 100;
    int i = 0;
    while (x < y) {
        x *= 1+p/100.;
        i++;
    }
    printf("%d\n", i);
    return 0;
}

