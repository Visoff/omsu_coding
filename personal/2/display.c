#include <stdio.h>
#include <stdlib.h>
#include <termios.h>
#include <time.h>
#include <unistd.h>
#include <wchar.h>
#include <pthread.h>

wchar_t readwchar();

void _clear_terminal() {
    printf("\e[1;1H\e[2J");
}

static struct termios default_tty;

void _reset_terminal() {
    tcsetattr(STDIN_FILENO, TCSANOW, &default_tty);
}

void _enable_raw_terminal() {
    tcgetattr(STDIN_FILENO, &default_tty);
    atexit(_reset_terminal);
    struct termios tty = default_tty;
    tty.c_lflag &= ~(ECHO | ICANON);
    tcsetattr(STDIN_FILENO, TCSANOW, &tty);
}

wchar_t readwchar() {
    int len = 0;
    char smc = getchar();
    wchar_t c = smc;
    while ((smc & (1 << (7-++len))) == 1) {}
    for (int i = 1; i < len; i++) {
        smc = getchar();
        c = (c << 8) | smc;
    }
    return c;
}
