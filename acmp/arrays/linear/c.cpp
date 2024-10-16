#include <stdio.h>
#include <malloc.h>

int main() {
    int n, l, r;
    scanf("%d", &n);
    int *arr = (int*)malloc(sizeof(int)*n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }
    int max_val = arr[0], min_val = arr[0];
    for (int i = 0; i < n; i++) {
        if (arr[i] < min_val) {
            min_val = arr[i];
        }
        if (arr[i] > max_val) {
            max_val = arr[i];
        }
    }
    for (int i = 0; i < n; i++) {
        if (arr[i] == max_val) {
            arr[i] = min_val;
        }
        printf("%d ", arr[i]);
    }
    printf("\n");
    return 0;
}

