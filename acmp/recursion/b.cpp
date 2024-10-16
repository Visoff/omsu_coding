#include <cstdio>

int main() {
    int n;
    scanf("%d", &n);
    if (n == 0) {
        printf("0\n");
    } else {
    	int p1 = 1, p2 = 1;
    	for (int i = 2; i < n; i++) {
    	    int t = p1 + p2;
	        p1 = p2;
        	p2 = t;
    	}
    	printf("%d\n", p2);
    }
    return 0;
}

