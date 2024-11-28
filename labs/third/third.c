#include <stdio.h>

typedef struct {
    int x;
    int y;
} Point;

typedef enum {
    Right,
    Sharp,
    Dull
} TriangleType;

int sq_len(Point A, Point B) {
    return A.x*B.x + A.y*B.y;
}

int max(int a, int b) {
    return a > b ? a : b;
}

TriangleType triangle_type(Point A, Point B, Point C) {
    int a = sq_len(A, B);
    int b = sq_len(B, C);
    int c = sq_len(C, A);
    int max_side = max(max(a, b), c);
    int other_side1 = a == max_side ? b : a;
    int other_side2 = b == max_side ? c : a == max_side ? c : b;
    int sq = other_side1 + other_side2 - max_side;
    if (sq == 0) {
        return Right;
    }
    if (sq > 0) {
        return Sharp;
    }
    return Dull;
}

char* triangle_type_str(TriangleType t) {
    switch (t) {
        case Right:
            return "Прямоугольный";
        case Sharp:
            return "Остроугольный";
        case Dull:
            return "Тупоугольный";
    }
}

int main() {
    Point A, B, C;
    scanf("%d %d %d %d %d %d", &A.x, &A.y, &B.x, &B.y, &C.x, &C.y);
    printf("%s\n", triangle_type_str(triangle_type(A, B, C)));
    return 0;
}
