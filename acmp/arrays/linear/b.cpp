#include <stdio.h>
#include <malloc.h>

int main() {
    int n, l, r;
    scanf("%d", &n);
    int *arr = (int*)malloc(sizeof(int)*n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }
    scanf("%d %d", &l, &r);
    int max_val = arr[l-1], max_i = l-1;
    for (int i = l-1; i < r; i++) {
        if (arr[i] > max_val) {
            max_val = arr[i];
            max_i = i;
        }
    }
    printf("%d %d\n", max_val, max_i+1);
    return 0;
}

