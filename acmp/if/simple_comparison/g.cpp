#include <cstdlib>
#include <stdio.h>

int main() {
    long a;
    scanf("%ld", &a);
    if (a == 1) {
        printf("0\n");
    } else {
    	printf("%ld\n", (a % 2 == 0) ? (a/2) : a);
    }
    return 0;
}

