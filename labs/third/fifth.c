#include <math.h>
#include <stdio.h>

int main() {
    int n;
    scanf("%d", &n);
    while (n <= 1 || n % 2 == 0) {
        scanf("%d", &n);
    }
    double S = 0;
    for (int i = 3; i <= n; i+=2) {
        S += 1.0 / sqrt((i-2)*i);
    }
    printf("%.2lf\n", S);
}
