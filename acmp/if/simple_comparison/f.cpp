#include <cstdlib>
#include <stdio.h>

int main() {
    int ga = 0;
    int gb = 0;
    int a, b;
    scanf("%d %d", &a, &b);
    ga += a;
    gb += b;
    scanf("%d %d", &a, &b);
    ga += a;
    gb += b;
    scanf("%d %d", &a, &b);
    ga += a;
    gb += b;
    scanf("%d %d", &a, &b);
    ga += a;
    gb += b;
    if (ga > gb) {
        printf("1\n");
    } else if (ga < gb) {
        printf("2\n");
    } else {
        printf("DRAW\n");
    }
    return 0;
}

