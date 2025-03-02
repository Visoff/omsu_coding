#include "lib/lib.h"
#include <stdio.h>

int main() {
    int res;
    int code = Proccess("test.txt", &res);
    if (code != 0) {
        return code;
    }
    printf("%d\n", res);
    return 0;
}
