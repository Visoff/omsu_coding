#include <cstdio>

int main() {
    int n;
    scanf("%d", &n);
    int rmin;
    int rmax;
    int curr;
    scanf("%d", &rmin);
    rmax = rmin;
    for (int i = 1; i < n; i++) {
        scanf("%d", &curr);
        if (curr < rmin) {rmin = curr;}
        if (curr > rmax) {rmax = curr;}
    }
    printf("%d %d\n", rmin, rmax);
    return 0;
}

