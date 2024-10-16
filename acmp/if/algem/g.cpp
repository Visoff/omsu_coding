#include <stdio.h>

int main() {
    int a, b, c;
    scanf("%d %d %d", &a, &b, &c);

    if (a == 0 && b == 0 && c == 0) {
        printf("0\n");
    } else {
        if (a != 0) {printf("%d", a);}

        switch (b) {
            case 0:
                break;
            case 1: 
                if (a != 0) {printf("-");}
                printf("x");
                break;
            case -1:
                printf("-x");
                break;
            default:
                if (a == 0) {
                    printf("%dx", b);
                } else {
                    printf("%+-dx", b);
                }
                break;
        }

        switch (c) {
            case 0:
                break;
            case 1:
                if (a != 0 || b != 0) {printf("-");}
                printf("y");
                break;
            case -1: 
                printf("-y");
                break;
            default:
                if (a == 0 && b == 0) {
                    printf("%dy", c);
                } else {
                    printf("%+-dy", c);
                }
                break;
        }

        printf("\n");
    }
    return 0;
}

