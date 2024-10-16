#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

uint8_t get_byte_len(char *s) {
    if (!((s[0] >> 7) & 1)) {
        return 1;
    }
    for (uint8_t i = 0; i <= 4; i++) {
        if (!((s[0] >> (7-i)) & 1)) {
            return i;
        }
    }
    return 4;
}

size_t unistrlen(char *s) {
    size_t len = 0;
    for (; s[0] != 0; s += get_byte_len(s)) {len++;}
    return len;
}

int main() {
    char *prefix = "Добрый день, ";
    size_t prefix_len = unistrlen(prefix);
    char *postfix = "!";
    size_t postfix_len = unistrlen(postfix);

    size_t bufsize = 1024;
    char *name = (char*)malloc(sizeof(char)*bufsize);
    size_t len = getline(&name, &bufsize, stdin);
    name[--len] = 0;
    size_t name_len = unistrlen(name);

    for (int i = 0; i < name_len+prefix_len+postfix_len+4; i++) {
        printf("*");
    }
    printf("\n");

    printf("* %s%s%s *\n", prefix, name, postfix);

    for (int i = 0; i < name_len+prefix_len+postfix_len+4; i++) {
        printf("*");
    }
    printf("\n");
}
