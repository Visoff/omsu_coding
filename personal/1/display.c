#include <stdio.h>
#include <stdlib.h>
#include <termios.h>
#include <unistd.h>

void _clear_terminal() {
    printf("\e[1;1H\e[2J");
}

static struct termios default_tty;

void reset_terminal() {
    tcsetattr(STDIN_FILENO, TCSANOW, &default_tty);
}

void enable_raw_terminal() {
    tcgetattr(STDIN_FILENO, &default_tty);
    atexit(reset_terminal);
    struct termios tty = default_tty;
    tty.c_lflag &= ~(ECHO | ICANON);
    tcsetattr(STDIN_FILENO, TCSANOW, &tty);
}

