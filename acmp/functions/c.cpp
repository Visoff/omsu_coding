#include <cstdlib>
#include <stdio.h>

long long fac(long long n) {
    if (n <= 1) {return 1;}
    return n * fac(n - 1);
}

int main() {
    long long n, k;
    scanf("%lld %lld", &n, &k);
    printf("%lld\n", (fac(n) / (fac(k) * fac(n - k))));
    return 0;
}

