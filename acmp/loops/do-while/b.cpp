#include <stdio.h>

int main() {
    int curr, i = 0;
    while (1) {
        scanf("%d", &curr);
        if (!curr) {
            printf("%d\n", i);
            break;
        }
        i += curr;
    }
    return 0;
}

