#include <cstdlib>
#include <stdio.h>

int main() {
    long a, b, c;
    scanf("%ld %ld %ld", &a, &b, &c);
    if (a*b == c) {
        printf("YES\n");
    } else {
        printf("NO\n");
    }
    return 0;
}

