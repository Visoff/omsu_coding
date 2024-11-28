#include <math.h>
#include <stdio.h>

#define E 2.71828182845904523536

void first() {
    double x;
    printf("x: ");
    scanf("%lf", &x);
    printf("%.2lf\n", 20*sin(x) - fabs(x*x*x - x*x));
}

void second() {
    double x, y;
    printf("x: ");
    scanf("%lf", &x);
    printf("y: ");
    scanf("%lf", &y);
    printf("%.2lf\n", fabs(x+y)/(x-y));
}

void third() {
    double x, y;
    printf("x: ");
    scanf("%lf", &x);
    printf("y: ");
    scanf("%lf", &y);
    printf("%.2lf\n", pow(2, x+y)*log2(x*y));
}

void fourth() {
    char names[] = "abcdef";
    double vars[6];
    for (int i = 0; i < 6; i++) {
        printf("%c: ", names[i]);
        scanf("%lf", &vars[i]);
    }
    printf("%.2lf\n", pow(vars[0]*vars[1]*(vars[2]*vars[3]-vars[4]*vars[5]), 1./6));
}

void fifth() {
    double x, y;
    printf("x: ");
    scanf("%lf", &x);
    printf("y: ");
    scanf("%lf", &y);
    printf("%.2lf\n", (x*y - 4*fabs(x+pow(E, x)))/(pow(10, 6) + sqrt(log(y))));
}

void sixth() {
    double x, y;
    printf("x: ");
    scanf("%lf", &x);
    printf("y: ");
    scanf("%lf", &y);
    printf("%.2lf\n", sin(2*(x+y)/(2+x)));
}

void seventh() {
    double x, y;
    printf("x: ");
    scanf("%lf", &x);
    printf("y: ");
    scanf("%lf", &y);
    printf("%.2lf\n", tan(x/3)*atan(y));
}

void eighth() {
    double x;
    printf("x: ");
    scanf("%lf", &x);
    printf("%.2lf\n", asin(x));
}

// jump table
void (*jmp[]) () = {
    first,
    second,
    third,
    fourth,
    fifth,
    sixth,
    seventh,
    eighth
};

int main() {
    int choice = 0;
    printf("Выберите выражение: ");
    while (choice < 1 || choice > 8) {scanf("%d", &choice);}
    jmp[choice-1]();
    return 0;
}
