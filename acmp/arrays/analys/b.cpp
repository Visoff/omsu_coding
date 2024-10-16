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
    for (int i = 0; i < m; i++) {
        int s, e;
        scanf("%d %d", &s, &e);
        for (int j = s-1; j < e; j++) {
            printf("%d ", arr[j]);
        }
        printf("\n");
    }
    return 0;
}

