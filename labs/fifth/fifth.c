#include <stdio.h>
#include <malloc.h>

int *find_max(int *arr, int len) {
    if (len == 0) {return arr;}
    int res_v = arr[0];
    int *res = arr;
    for (int i = 0; i < len; i++) {
        if (res_v < arr[i]) {
            res_v = arr[i];
            res = &arr[i];
        }
    }
    return res;
}

int main(int argc, char **argv) {
    if (argc != 3) {
        printf("usage: %s <filepath>", argv[0]);
        return 1;
    }
    char *filepath = argv[1];
    FILE *f = fopen(filepath, "r");
    int size;
    fscanf(f, "%d", &size);
    int *arr = (int*)malloc(sizeof(int)*size);
    for (int i = 0; i < size; i++) {
        int l = fscanf(f, "%d", &arr[i]);
        if (l == -1) {
            printf("Error not enough values\n");
            return 1;
        }
    }
    printf("%d\n", *find_max(arr, size));
}
