#include <stdio.h>

int main() {
    int a, b, c, d;
    scanf("%d %d %d %d", &a, &b, &c, &d);
    if (a != c || d == b || b == 1) {
        printf("NO\n");
    } else if (b == 2) {
        if (d-b <= 2) {
            printf("YES\n");
        } else {
            printf("NO\n");
        }
    } else if (d-b == 1) {
        printf("YES\n");
    } else {
        printf("NO\n");
    }
    return 0;
}


