#include <stdio.h>

int main() {
    float a = 6, c = 4, b;
    b = a * 2 / 3 * c;
    printf("%f\n", b);
    b = (a*2)/(3*c);
    printf("%f\n", b);
    return 0;
}
