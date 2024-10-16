#include <stdio.h>
#include <stdlib.h>

int main() {
    int arr[10];
    arr[0] = rand();
    printf("%d ", arr[0]);
    int min_val = arr[0];
    for (int i = 1; i < 10; i++) {
        arr[i] = rand();
        printf("%d ", arr[i]);
        if (min_val > arr[i]) {
            min_val = arr[i];
        }
    }
    printf("\n%d\n", min_val);
    return 0;
}

