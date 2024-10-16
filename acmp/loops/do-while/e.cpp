#include <stdio.h>

int main() {
    int curr, res = 0;
    while (1) {
        scanf("%d", &curr);
        if (!curr) {
            printf("%d\n", res);
            break;
        }
        if (curr > res) {res = curr;}
    }
    return 0;
}

