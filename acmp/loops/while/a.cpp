#include <cstdio>

int main() {
    int n;
    scanf("%d", &n);
    for (int i = 1; i*i <= n; i++) {
        printf("%d\n", i*i);
    }
    return 0;
}

