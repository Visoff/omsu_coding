#include <cstdlib>
#include <stdio.h>
#include <malloc.h>

int prime_or_zero(int n) {
    if (n < 2) {return 0;}
    int k = n/2;
    while (n%k) {k-=1;}
    return k > 1 ? 0 : n;
}

int main() {
    int a, b, c;
    scanf("%d %d %d", &a, &b, &c);
    int sum = prime_or_zero(a)+prime_or_zero(b)+prime_or_zero(c);
    printf("%d\n%s\n", sum, prime_or_zero(sum) ? "Yes" : "No");
    return 0;
}

