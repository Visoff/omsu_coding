#include <stdio.h>
#include <malloc.h>

int main() {
    int n, l, r;
    scanf("%d", &n);
    int *arr = (int*)malloc(sizeof(int)*n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }
    int m;
    scanf("%d", &m);
    for (int i = 0; i <= n; i++) {
        if (i == n || arr[i] < m) {
            printf("%d\n", i+1);
            break;
        }
    }
    return 0;
}

