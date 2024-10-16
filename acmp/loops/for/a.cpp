#include <cstdio>

int main() {
    int n;
    scanf("%d", &n);
    int counter = 0;
    int coin;
    for (int i = 0; i < n; i++) {
        scanf("%d", &coin);
        counter += coin;
    }
    if (counter*2 >= n) {
        printf("%d\n", n-counter);
    } else {
        printf("%d\n", counter);
    }
    return 0;
}

