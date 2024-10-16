#include <stdio.h>

int main() {
    double x, y;
    printf("x: ");
    scanf("%lf", &x);
    printf("y: ");
    scanf("%lf", &y);
    int precision, num;
    printf("places: ");
    scanf("%d", &num);
    printf("precision: ");
    scanf("%d", &precision);
    printf("%*.*lf\n", num, precision, x + y);
    printf("%*.*lf\n", num, precision, x - y);
    printf("%*.*lf\n", num, precision, x * y);
    printf("%*.*lf\n", num, precision, x / y);
    return 0;
}
