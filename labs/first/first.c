#include <stdio.h>

int main() {
    float x = 829.356;
    float y = 128.286;
    printf("%.3f + %.3f = %.3f\n", x, y, x + y);
    printf("%.6e\n", x);
    printf("%.2e\n", y);
    printf("%.2f\n", x);
    printf("%13.3f\n", y);
    return 0;
}
