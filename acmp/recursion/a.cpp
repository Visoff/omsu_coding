#include <cstdlib>
#include <stdio.h>

int main() {
    int n;
    int buf[1024];
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        int l = scanf("%d", buf+i);
    }

    for (int i = 0; i < n; i++) {
        printf("%d ", buf[n-1-i]);
    }
    printf("\n");

    return 0;
}

