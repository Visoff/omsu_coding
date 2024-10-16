#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>

int main(int argc, char **argv) {
    if (argc < 2) {
        printf("usage: %s <filepath> or %s <filepath> <number>", argv[0], argv[0]);
        return 1;
    }

    char *filepath = argv[1];
    if (argc == 3) {
        FILE *f = fopen(filepath, "w");
        int n = atoi(argv[2]);
        for (char i = 0; i < n; i++) {
            fprintf(f, "%c ", i);
        }
        fprintf(f, "\n");
        return 0;
    }
    
    FILE *f = fopen(filepath, "r");
    int len, i = 0;
    scanf("%d", &len);
    int *arr = (int*)malloc(sizeof(int)*len);
    for (int i = 0; i < len; i++) {
        char c;
        int l = fscanf(f, "%c", &c);
        if (l == -1) {
            printf("Error not enough values\n");
            return 1;
        }
        arr[i++] = c;
        if (i == len) {
            printf("Error too many values\n");
            return 1;
        }
    }
    for (int i = 0; i < len; i++) {
        printf("%c ", arr[i]);
    }
    printf("\n");
    return 0;
}
