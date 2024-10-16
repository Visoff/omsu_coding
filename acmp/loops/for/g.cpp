#include <cstdio>

int main() {
    int num;
    scanf("%d", &num);
    int res = 0, curr, sex;
    for (int i = 0; i < num; i++) {
        for (int j = 0; j < num; j++) {
            scanf("%d", &curr);
            res += curr;
        }
    }
    printf("%d\n", res/2);
    return 0;
}

