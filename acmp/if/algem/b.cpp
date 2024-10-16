#include <stdio.h>


int main() {
    int a, b, c;
    scanf("%d %d %d", &a, &b, &c);
    if (b+c <= a) {
        printf("YES\n");
    } else {
        printf("NO\n");
    }
    return 0;
}

