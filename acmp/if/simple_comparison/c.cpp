#include <cstdlib>
#include <stdio.h>

int main() {
    char buf[7];
    scanf("%s", buf);
    if (buf[0] + buf[1] + buf[2] == buf[3] + buf[4] + buf[5]) {
        printf("YES\n");
    } else {
        printf("NO\n");
    }
    return 0;
}

