#include <cstdlib>
#include <stdio.h>
#include <malloc.h>

int main() {
    long long x1, y1, x2, y2, x3, y3;
    scanf("%lld %lld %lld %lld %lld %lld", &x1, &y1, &x2, &y2, &x3, &y3);
    double S = 0.5 * std::abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
    printf("%lf\n", S);
    return 0;
}

