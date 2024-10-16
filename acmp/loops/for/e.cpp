#include <cstdio>

int main() {
    int num;
    scanf("%d", &num);
    int max_val = 0, max_idx = -1, curr, sex;
    for (int i = 1; i <= num; i++) {
        scanf("%d %d", &curr, &sex);
        if (sex && curr > max_val) {
            max_val = curr;
            max_idx = i;
        }
    }
    printf("%d\n", max_idx);
    return 0;
}

