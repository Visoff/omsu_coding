#include <cstdlib>
#include <stdio.h>

typedef struct {
    int x;
    int y;
} Point;

int pythagore(Point p) {
    return p.x * p.x + p.y * p.y;
}

Point sub(Point p1, Point p2) {
    Point res;
    res.x = p1.x - p2.x;
    res.y = p1.y - p2.y;
    return res;
}

Point scan_point() {
    Point p;
    scanf("%d %d", &p.x, &p.y);
    return p;
}

int main() {
    Point p1 = scan_point();
    Point p2 = scan_point();
    Point p3 = scan_point();
    int s1 = pythagore(sub(p1, p2));
    int s2 = pythagore(sub(p2, p3));
    int s3 = pythagore(sub(p3, p1));
    if (s1+s2 == s3 || s1+s3 == s2 || s2+s3 == s1) {
        printf("Yes\n");
    } else {
        printf("No\n");
    }
    return 0;
}

