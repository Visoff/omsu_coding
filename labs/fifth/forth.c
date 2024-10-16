#include <stdio.h>
#include <malloc.h>

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
    for (int i = 0; i < size; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}
