#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>

struct termios default_tty;

void disable_raw_terminal() {
    tcsetattr(STDIN_FILENO, TCSANOW, &default_tty);
}

void enable_raw_terminal() {
    tcgetattr(STDIN_FILENO, &default_tty);
    atexit(disable_raw_terminal);

    struct termios tty;
    
    tcgetattr(STDIN_FILENO, &tty);

    tty.c_cc[VMIN] = 1;
    tty.c_cc[VTIME] = 0;

    tty.c_lflag &= ~(ICANON | ECHO | ISIG);

    tcsetattr(STDIN_FILENO, TCSANOW, &tty);
}

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

uint8_t ch_lt(char *a, char *b) {
    int a_len = get_byte_len(a);
    int b_len = get_byte_len(b);
    if (a_len < b_len) {return 1;}
    if (a_len > b_len) {return 0;}
    for (uint8_t i = 0; i < a_len; i++) {
        if (a[i] < b[i]) {return 1;}
        if (a[i] > b[i]) {return 0;}
    }
    return 0;
}

uint8_t ch_eq(char *a, char *b) {
    int a_len = get_byte_len(a);
    int b_len = get_byte_len(b);
    if (a_len != b_len) {return 0;}
    for (uint8_t i = 0; i < a_len; i++) {
        if (a[i] != b[i]) {return 0;}
    }
    return 1;
}

typedef enum {
    Latin,
    Digit,
    Russian,
    Punctiation,
    Other
} CharacterType;

CharacterType ch_type(char *s) {
    int len = get_byte_len(s);
    if (len == 1) {
        char c = s[0];
        if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
            return Latin;
        }
        if ('0' <= c && c <= '9') {
            return Digit;
        }
        if ('!' <= c && c <= '/') {
            return Punctiation;
        }
        return Other;
    }
    if (len == 2) {
        if ((ch_lt("а", s) || ch_eq("а", s)) && (ch_lt("ё", s) || ch_eq("ё", s))) {
            return Russian;
        }
        if ((ch_lt("А", s) || ch_eq("А", s)) && (ch_lt("Ё", s) || ch_eq("Ё", s))) {
            return Russian;
        }
    }
    return Other;
}

char* ch_type_name(CharacterType t) {
    switch (t) {
        case Latin:
            return "латинский";
        case Digit:
            return "цифры";
        case Russian:
            return "русский";
        case Punctiation:
            return "знак припинания";
        case Other:
            return "другое";
    }
}

int main() {
    enable_raw_terminal();

    char c[3];
    while (read(STDIN_FILENO, &c, 3) > 0) {
        // ^C -> <C-C>
        // ^[ -> <Esc>
        if (c[0] == '' || c[0] == '' || c[0] == '\n') {
            break;
        }
        printf("%s %s\n", c, ch_type_name(ch_type(c)));
    }
    return 0;
}
