#include <stdio.h>

int max(int a, int b) {
    return a > b ? a : b;
}

int main() {
    int a, b, c;
    scanf("%d %d %d", &a, &b, &c);
    int sum = a + b + c;
    if (2 * max(max(a, b), c) >= sum) {
        printf("Невозможно\n");
        return 0;
    }
    printf("Возможно\n");
    return 0;
}
