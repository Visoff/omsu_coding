#include <cstdio>

int main() {
    long long a, b, c, d;
    scanf("%lld %lld %lld %lld", &a, &b, &c, &d);
    for (long long i = -100; i <= 100; i++) {
        if (!(a*i*i*i + b*i*i + c*i + d)) {
            printf("%lld ", i);
        }
    }
    printf("\n");
    return 0;
}

