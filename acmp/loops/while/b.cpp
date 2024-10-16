#include <cstdio>

int main() {
    int n;
    scanf("%d", &n);
    int i;
    for (i = 2; n % i != 0; i++) {}
    printf("%d\n", i);
    return 0;
}

