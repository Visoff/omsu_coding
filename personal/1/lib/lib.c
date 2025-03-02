#include "lib.h"

#include <stdio.h>
#include <stdlib.h>

struct FatArray {
    int* arr;
    int size;
    int capacity;
};

void array_push_back(struct FatArray* arr, int value) {
    if (arr->size >= arr->capacity) {
        arr->capacity *= 2;
        arr->arr = (int*)realloc(arr->arr, sizeof(int) * arr->capacity);
    }

    arr->arr[arr->size++] = value;
}

struct FatArray read_sequance(FILE *fp) {
    struct FatArray arr;
    arr.size = 0;
    arr.capacity = 1;
    arr.arr = (int*)malloc(sizeof(int));

    int value;
    while (fscanf(fp, "%d", &value) == 1) {
        array_push_back(&arr, value);
    }

    return arr;
}

extern int Proccess(char *filepath, int *res) {
    FILE *fp = fopen(filepath, "r");
    if (fp == NULL) {
        return 1;
    }

    struct FatArray arr = read_sequance(fp);
    if (arr.size == 0) {
        return 2;
    }

    int in_subsequance = 0;
    int count = 0;
    int sum = 0;
    for (int i = 0; i < arr.size; i++) {
        if (arr.arr[i] % 5 != 0) {
            if (in_subsequance != 0) {
                count++;
                sum+=in_subsequance;
            }
            in_subsequance = 0;
            continue;
        }
        in_subsequance++;
    }
    if (in_subsequance != 0) {
        count++;
        sum+=in_subsequance;
    }

    *res = sum/count;
    free(arr.arr);
    return 0;
}
