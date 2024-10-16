#include <stdio.h>

int main() {
    int a, b, c;
    scanf("%d %d %d", &a, &b, &c);
    if (a < 0 || b < 0 || c < 0) {
        printf("Не все числа положительны");
        return 0;
    }
    printf("Все числа положительны");
    return 0;
}
