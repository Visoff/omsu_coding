#include <stdio.h>

int main() {
    int curr, s = 0, c = 0;
    while (1) {
        scanf("%d", &curr);
        if (!curr) {
            printf("%lf\n", double(s)/double(c));
            break;
        }
        c++;
        s += curr;
    }
    return 0;
}

