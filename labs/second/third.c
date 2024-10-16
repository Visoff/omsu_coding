#include <stdio.h>
#include <math.h>

typedef struct {
    double x, y;
} Vector;

typedef Vector Point;

Vector sub(Point a, Point b) {
    return (Vector) {a.x - b.x, a.y - b.y};
}

double sq_len(Vector p) {
    return p.x*p.x + p.y*p.y;
}

int main() {
    Point A, B, C;
    printf("A: ");
    scanf("%lf %lf", &A.x, &A.y);
    printf("B: ");
    scanf("%lf %lf", &B.x, &B.y);
    printf("C: ");
    scanf("%lf %lf", &C.x, &C.y);
    double sq_a = sq_len(sub(A, B));
    double sq_b = sq_len(sub(A, C));
    double sq_c = sq_len(sub(B, C));
    double m = 0.5 * sqrt(2*sq_a + 2*sq_b - sq_c);
    printf("%.2lf\n", m);
    return 0;
}
