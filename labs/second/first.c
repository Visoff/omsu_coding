#include <stdio.h>

int main() {
    float a, b, c, d, e, f;
    printf("ax + by = e\n");
    scanf("%f %f %f", &a, &b, &e);
    printf("cx + dy = f\n");
    scanf("%f %f %f", &c, &d, &f);
    float delta = a * d - b * c;
    float delta_x = e * d - b * f;
    float delta_y = a * f - c * e;
    if (delta == 0) {
        if (delta_x == 0 || delta_y == 0) {
            printf("x, y - любое");
            return 0;
        }
        printf("Нет решений");
        return 0;
    }
    float x = delta_x / delta;
    float y = delta_y / delta;
    printf("x = %.2f\ny = %.2f\n", x, y);
    return 0;
}
