#include <cstdio>
#include <cstdlib>

int main() {
    int a, b, c, d;
    scanf("%d %d %d %d", &a, &b, &c, &d);
    if (a == c && b == d) {
        printf("NO\n");
    } else if (abs(a-c) <= 1 && abs(b-d) <= 1) {
        printf("YES\n");
    } else {
        printf("NO\n");
    }
    return 0;
}

