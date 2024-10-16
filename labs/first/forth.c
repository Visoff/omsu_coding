#include <stdio.h>
#include <string.h>

int main() {
    char buf[1024];
    printf("Your name: ");
    scanf("%s", buf);
    char greating[1024] = "Hello, ";
    strcat(greating, buf);
    strcat(greating, "!");
    printf("%40s\n", greating);
    return 0;
}
