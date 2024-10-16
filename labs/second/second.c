#include <stdio.h>
#include <math.h>

double sq_len(int x, int y) {
    return x*x + y*y;
}

int main() {
    double x1, y1, x2, y2, x3, y3;
    printf("A: ");
    scanf("%lf %lf", &x1, &y1);
    printf("B: ");
    scanf("%lf %lf", &x2, &y2);
    printf("C: ");
    scanf("%lf %lf", &x3, &y3);
    double sq_a = sq_len(x2 - x1, y2 - y1);
    double sq_b = sq_len(x3 - x1, y3 - y1);
    double sq_c = sq_len(x3 - x2, y3 - y2);
    double m = 0.5 * sqrt(2*sq_a + 2*sq_b - sq_c);
    printf("%.2lf\n", m);
    return 0;
}
