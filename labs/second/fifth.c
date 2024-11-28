#include <math.h>
#include <stdio.h>

// f: double -> double
double f(double x, double y, double alfa) {
    return x*2 + sin(y*2 - alfa)/fabs(x*4 - exp(-5))*x;
}

int main() {
    printf("x² + sin(y² - α)/|x⁴ - e⁻⁵| * x\n");
    return 0;
}
