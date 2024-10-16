#include <cstdio>

int main() {
    int num;
    scanf("%d", &num);
    int n, m;
    for (int i = 0; i < num; i++) {
        scanf("%d %d", &n, &m);
        printf("%d\n", 19*m + (n+239)*(n+366)/2);
    }
    return 0;
}

