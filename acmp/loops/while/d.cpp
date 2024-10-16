#include <cstdio>

int main() {
    int n;
    scanf("%d", &n);
    int i;
    for (i = n; i > 1 && i % 2 == 0; i/=2) {}
    printf("%s\n", i == 1 ? "YES" : "NO");
    return 0;
}

