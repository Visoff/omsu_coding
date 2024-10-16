#include <cstdio>
#include <cstdlib>

int main() {
    int a, b, c, d;
    scanf("%d %d %d %d", &a, &b, &c, &d);
    if (abs(a-c) == abs(b-d)) {
        printf("YES\n");
    } else {
        printf("NO\n");
    }
    return 0;
}

