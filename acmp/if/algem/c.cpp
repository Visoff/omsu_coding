#include <cmath>
#include <stdio.h>

int main() {
    int a, b, c, d;
    scanf("%d %d %d %d", &a, &b, &c, &d);
    double x = c-a;
    double y = d-b;
    double sq = x*x + y*y;
    printf("%.5lf\n", sqrt(sq));
    return 0;
}

